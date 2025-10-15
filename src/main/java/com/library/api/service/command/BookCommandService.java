package com.library.api.service.command;

import com.library.api.command.book.CreateBookCommand;
import com.library.api.command.book.DeleteBookCommand;
import com.library.api.dto.books.BookRequestDTO;
import com.library.api.exception.BookNotFoundException;
import com.library.api.mapper.BookMapper;
import com.library.api.model.Book;
import com.library.api.model.User;
import com.library.api.repository.BookRepository;
import com.library.api.service.AuthenticationInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookCommandService {

    private final BookRepository bookRepository;
    private final AuthenticationInformation authenticationInformation;


    public Long create(CreateBookCommand command){
        User author = authenticationInformation.getAuthenticatedUser();
        Book savedBook = bookRepository.save(BookMapper.dtoToBook(command, author));
        return savedBook.getId();
    }

    public void delete(DeleteBookCommand command) {
        Book book = bookRepository.findById(command.bookId()).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }
}
