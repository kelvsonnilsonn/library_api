package com.library.api.service.command;

import com.library.api.command.borrow.BorrowBookCommand;
import com.library.api.command.borrow.ReturnBookCommand;
import com.library.api.event.borrow.BookBorrowedEvent;
import com.library.api.event.borrow.BookReturnedEvent;
import com.library.api.exception.BookAlreadyBorrowedException;
import com.library.api.exception.BorrowNotFoundException;
import com.library.api.model.Book;
import com.library.api.model.Borrow;
import com.library.api.model.User;
import com.library.api.repository.BorrowRepository;
import com.library.api.service.AuthenticationInformation;
import com.library.api.service.EventStoreService;
import com.library.api.service.query.BookQueryService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    private final EventStoreService eventStoreService;

    @CacheEvict(value = {"books", "borrows", "my-borrows", "my-overdues"}, allEntries = true)
    public void borrowBook(BorrowBookCommand command){
        User user = authenticationInformation.getAuthenticatedUser();
        Book book = bookQueryService.findEntityById(command.bookId());
        if(borrowRepository.existsByBookAndReturnDateIsNull(book)){
            throw new BookAlreadyBorrowedException();
        }
        Borrow bookToBorrow = new Borrow(user, book, LocalDateTime.now().plusDays(7));
        borrowRepository.save(bookToBorrow);
        BookBorrowedEvent event = new BookBorrowedEvent(bookToBorrow.getId(), book.getId(), user.getId(), bookToBorrow.getDueDate());
        eventStoreService.saveEvent(AppConstants.AGGREGATE_BORROW_TYPE, bookToBorrow.getId(), event);
    }

    @CacheEvict(value = {"books", "borrows", "my-borrows", "my-overdues"}, allEntries = true)
    public void returnBook(ReturnBookCommand command){
        Long userId = authenticationInformation.getAuthenticatedUser().getId();
        Borrow borrowedBook = borrowRepository.findByBookIdAndUserAndReturnDateIsNull(command.bookId(), userId)
                .orElseThrow(BorrowNotFoundException::new);
        borrowedBook.setReturnDate(LocalDateTime.now());
        borrowRepository.save(borrowedBook);
        BookReturnedEvent event = new BookReturnedEvent(borrowedBook.getId(),
                borrowedBook.getBookId(), userId,
                borrowedBook.getReturnDate(), borrowedBook.getBorrowDate(), borrowedBook.wasOverdue());
        eventStoreService.saveEvent(AppConstants.AGGREGATE_BORROW_TYPE, borrowedBook.getId(), event);
    }
}
