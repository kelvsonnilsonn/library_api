package com.library.api.service;

import com.library.api.dto.BookRequestDTO;
import com.library.api.dto.BookResponseDTO;
import com.library.api.exception.BookNotFoundException;
import com.library.api.mapper.BookMapper;
import com.library.api.model.Book;
import com.library.api.model.User;
import com.library.api.repository.BookRepository;
import com.library.api.util.AppConstants;
import org.springframework.stereotype.Service;

// V1.2

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserService userService;

    public BookService(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public BookResponseDTO create(BookRequestDTO book){
        User author = userService.findEntityById(book.authorId());
        Book savedBook = bookRepository.save(BookMapper.dtoToBook(book, author));
        return BookMapper.toResponse(savedBook);
    }

    public String delete(Long id) {
        Book book = findEntityById(id);
        bookRepository.deleteById(id);
        return String.format(AppConstants.BOOK_DELETED_MSG, book.getTitle());
    }

    public BookResponseDTO findById(Long id){
        Book book = findEntityById(id);
        return BookMapper.toResponse(book);
    }

    public BookResponseDTO findByIsbn(String isbn){
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        return BookMapper.toResponse(book);
    }

    public Book findEntityById(Long id){
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }
}
