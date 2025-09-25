package com.library.api.service;

import com.library.api.dto.BookDTO;
import com.library.api.model.Book;
import com.library.api.model.ISBN;
import com.library.api.model.User;
import com.library.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// V1.0

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
        ISBN isbn = new ISBN(book.isbn());
        Book createBook = new Book(author, book.type(), isbn, book.title());
        bookRepository.save(createBook);
    }
}
