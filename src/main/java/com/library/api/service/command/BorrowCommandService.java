package com.library.api.service.command;

import com.library.api.command.borrow.BorrowBookCommand;
import com.library.api.command.borrow.ReturnBookCommand;
import com.library.api.dto.borrow.BorrowRequestDTO;
import com.library.api.dto.borrow.BorrowResponseDTO;
import com.library.api.exception.BookAlreadyBorrowedException;
import com.library.api.exception.BorrowNotFoundException;
import com.library.api.model.Book;
import com.library.api.model.Borrow;
import com.library.api.model.User;
import com.library.api.repository.BorrowRepository;
import com.library.api.service.AuthenticationInformation;
import com.library.api.service.query.BookQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class BorrowCommandService {

    private final BorrowRepository borrowRepository;
    private final BookQueryService bookQueryService;
    private final AuthenticationInformation authenticationInformation;

    public void borrowBook(BorrowBookCommand command){
        User user = authenticationInformation.getAuthenticatedUser();
        Book book = bookQueryService.findEntityById(command.bookId());
        if(borrowRepository.existsByBookAndReturnDateIsNull(book)){
            throw new BookAlreadyBorrowedException();
        }
        Borrow bookToBorrow = new Borrow(user, book, LocalDateTime.now().plusDays(7));
        borrowRepository.save(bookToBorrow);
    }

    public void returnBook(ReturnBookCommand command){
        User user = authenticationInformation.getAuthenticatedUser();
        Borrow borrowedBook = borrowRepository.findByBookIdAndUserAndReturnDateIsNull(command.bookId(), user)
                .orElseThrow(BorrowNotFoundException::new);
        borrowedBook.setReturnDate(LocalDateTime.now());
        borrowRepository.save(borrowedBook);
    }
}
