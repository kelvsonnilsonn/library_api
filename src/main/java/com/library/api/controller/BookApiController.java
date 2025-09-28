package com.library.api.controller;

import com.library.api.dto.BookRequestDTO;
import com.library.api.dto.BookResponseDTO;
import com.library.api.service.BookService;
import com.library.api.util.AppConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// V1.2

@RestController
@RequestMapping(AppConstants.BOOK_BASE_PATH)
public class BookApiController implements BookAPI{

    private final BookService bookService;

    public BookApiController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping(AppConstants.CREATE_PATH)
    public ResponseEntity<BookResponseDTO> create(@RequestBody BookRequestDTO bookRequestDTO){
        BookResponseDTO response = bookService.create(bookRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(AppConstants.DELETE_PATH)
    public ResponseEntity<String> delete(@RequestParam Long id){
        String message = bookService.delete(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping(AppConstants.SEARCH_PATH)
    public ResponseEntity<BookResponseDTO> findById(@RequestParam Long id) {
        BookResponseDTO response = bookService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(AppConstants.SEARCH_ISBN_PATH)
    public ResponseEntity<BookResponseDTO> findByIsbn(@RequestParam String isbn) {
        BookResponseDTO response = bookService.findByIsbn(isbn);
        return ResponseEntity.ok(response);
    }
}
