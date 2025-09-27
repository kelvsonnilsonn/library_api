package com.library.api.controller;

import com.library.api.dto.BookRequestDTO;
import com.library.api.dto.BookResponseDTO;
import com.library.api.service.BookService;
import com.library.api.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// V1.1

@RestController
@RequestMapping(AppConstants.BOOK_BASE_PATH)
public class BookController implements BookAPI{

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping(AppConstants.CREATE_BOOK_PATH)
    public ResponseEntity<BookResponseDTO> create(@RequestBody BookRequestDTO bookRequestDTO){
        BookResponseDTO response = bookService.create(bookRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id){
        String message = bookService.delete(id);
        return ResponseEntity.ok(message);
    }

    @Override
    public ResponseEntity<BookResponseDTO> findById(@RequestParam Long id) {
        BookResponseDTO dto = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }
}
