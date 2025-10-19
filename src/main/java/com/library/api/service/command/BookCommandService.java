package com.library.api.service.command;

import com.library.api.command.book.CreateBookCommand;
import com.library.api.command.book.DeleteBookCommand;
import com.library.api.event.book.BookCreatedEvent;
import com.library.api.event.book.BookDeletedEvent;
import com.library.api.exception.BookNotFoundException;
import com.library.api.mapper.BookMapper;
import com.library.api.model.Book;
import com.library.api.model.User;
import com.library.api.repository.BookRepository;
import com.library.api.service.AuthenticationInformation;
import com.library.api.service.EventStoreService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookCommandService {

    private final BookRepository bookRepository;
    private final AuthenticationInformation authenticationInformation;
    private final EventStoreService eventStoreService;

    public Long create(CreateBookCommand command){
        User author = authenticationInformation.getAuthenticatedUser();
        Book savedBook = bookRepository.save(BookMapper.dtoToBook(command, author));
        BookCreatedEvent event = new BookCreatedEvent(savedBook.getId(), savedBook.getIsbnNumber(), author.getId());
        eventStoreService.saveEvent(AppConstants.AGGREGATE_BOOK_TYPE, savedBook.getId(), event);
        return savedBook.getId();
    }

    public void delete(DeleteBookCommand command) {
        Long userId = authenticationInformation.getAuthenticatedUser().getId();
        Book book = bookRepository.findByBookIdAndAuthorId(command.bookId(), userId).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
        BookDeletedEvent event = new BookDeletedEvent(book.getId(), command.reason(), userId);
        eventStoreService.saveEvent(AppConstants.AGGREGATE_BOOK_TYPE, book.getId(), event);
    }

    public void deleteByAdmin(DeleteBookCommand command) {
        Book book = bookRepository.findById(command.bookId()).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
        BookDeletedEvent event = new BookDeletedEvent(book.getId(), command.reason(), authenticationInformation.getAuthenticatedUser().getId());
        eventStoreService.saveEvent(AppConstants.AGGREGATE_BOOK_TYPE, book.getId(), event);
    }
}
