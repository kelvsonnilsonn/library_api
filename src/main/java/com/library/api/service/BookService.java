package com.library.api.service;

import com.library.api.dto.BookDTO;
import com.library.api.exception.BookNotFoundException;
import com.library.api.mapper.BookMapper;
import com.library.api.model.Book;
import com.library.api.model.User;
import com.library.api.repository.BookRepository;
import com.library.api.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// V1.1

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserService userService;

    @Autowired
    public BookService(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public void create(BookDTO book){
        User author = userService.findById(book.authorId());
        Book createBook = BookMapper.dtoToBook(book, author);
        bookRepository.save(createBook);
    }

    public String delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
        return String.format(AppConstants.BOOK_DELETED_MSG, book.getTitle());
    }

    public BookDTO findById(Long id){
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return BookMapper.bookToDTO(book);
    }
}
