package com.library.api.controller;

import com.library.api.dto.BookDTO;
import com.library.api.service.BookService;
import com.library.api.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// V1.0

@RestController
@RequestMapping(AppConstants.BOOK_BASE_PATH)
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping(AppConstants.CREATE_BOOK_PATH)
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO){
        bookService.create(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
    }
}
