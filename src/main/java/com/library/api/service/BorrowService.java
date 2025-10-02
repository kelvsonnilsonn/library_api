package com.library.api.service;

import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.borrow.BorrowRequestDTO;
import com.library.api.dto.borrow.BorrowResponseDTO;
import com.library.api.exception.BookAlreadyBorrowedException;
import com.library.api.exception.BorrowNotFoundException;
import com.library.api.mapper.BorrowMapper;
import com.library.api.model.Book;
import com.library.api.model.Borrow;
import com.library.api.model.User;
import com.library.api.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

// V1.2

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookService bookService;
    private final BorrowMapper borrowMapper;
    private final AuthenticationInformation authenticationInformation;

    @Transactional
    public BorrowResponseDTO borrowBook (BorrowRequestDTO borrowDTO){

        User user = authenticationInformation.getAuthenticatedUser();
        Book book = bookService.findEntityById(borrowDTO.bookId());

        if(borrowRepository.existsByBookAndReturnDateIsNull(book)){
            throw new BookAlreadyBorrowedException();
        }

        Borrow bookToBorrow = new Borrow(user, book, borrowDTO.dueDate());
        Borrow savedBorrow = borrowRepository.save(bookToBorrow);

        return borrowMapper.toResponse(savedBorrow);
    }

    @Transactional
    public BorrowResponseDTO returnBook(Long bookId){
        User user = authenticationInformation.getAuthenticatedUser();

        Borrow borrowedBook = borrowRepository.findByBookIdAndUserAndReturnDateIsNull(bookId, user)
                .orElseThrow(BorrowNotFoundException::new);

        borrowedBook.setReturnDate(LocalDateTime.now());
        Borrow updatedBorrow = borrowRepository.save(borrowedBook);

        return borrowMapper.toResponse(updatedBorrow);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<BorrowResponseDTO> findAllMyBorrows(Pageable pageable){
        Page<Borrow> borrows = borrowRepository.findByUserAndReturnDateIsNull(pageable, authenticationInformation.getAuthenticatedUser());
        Page<BorrowResponseDTO> page = borrows.map(borrowMapper::toResponse);
        return PageResponseDTO.fromPage(page);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<BorrowResponseDTO> findOverdueBorrows(Pageable pageable){
        Page<Borrow> borrows = borrowRepository.findOverdueByUser(pageable, authenticationInformation.getAuthenticatedUser(), LocalDateTime.now());
        Page<BorrowResponseDTO> page = borrows.map(borrowMapper::toResponse);
        return PageResponseDTO.fromPage(page);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<BorrowResponseDTO> getUserBorrowHistory(Pageable pageable){
        Page<BorrowResponseDTO> page = borrowRepository.getUserBorrowHistory(pageable, authenticationInformation.getAuthenticatedUser()).map(borrowMapper::toResponse);
        return PageResponseDTO.fromPage(page);
    }
}
