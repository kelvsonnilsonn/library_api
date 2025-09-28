package com.library.api.controller;

import com.library.api.dto.BookRequestDTO;
import com.library.api.dto.BookResponseDTO;
import com.library.api.service.BookService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// V1.4

@RestController
@RequestMapping(AppConstants.BOOK_BASE_PATH)
@RequiredArgsConstructor
public class BookApiController implements BookAPI{

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@RequestBody BookRequestDTO bookRequestDTO){
        BookResponseDTO response = bookService.create(bookRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(AppConstants.ID_PATH)
    public ResponseEntity<String> delete(@PathVariable Long id){
        String message = bookService.delete(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping(AppConstants.ID_PATH)
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Long id) {
        BookResponseDTO response = bookService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(AppConstants.SEARCH_ISBN_PATH)
    public ResponseEntity<BookResponseDTO> findByIsbn(@PathVariable String isbn) {
        BookResponseDTO response = bookService.findByIsbn(isbn);
        return ResponseEntity.ok(response);
    }
}
