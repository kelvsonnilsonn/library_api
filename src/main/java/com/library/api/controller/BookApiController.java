package com.library.api.controller;

import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.books.BookRequestDTO;
import com.library.api.dto.books.BookResponseDTO;
import com.library.api.service.BookService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BOOK_BASE_PATH)
@RequiredArgsConstructor
public class BookApiController implements BookAPI{

    private final BookService bookService;
    private final ContentVerifier<BookResponseDTO> contentVerifier;

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

    @GetMapping
    public ResponseEntity<PageResponseDTO<BookResponseDTO>> findAll(Pageable pageable){
        PageResponseDTO<BookResponseDTO> books = bookService.findAll(pageable);
        return contentVerifier.verifyingContent(books);
    }

    @GetMapping(AppConstants.SEARCH_AVAILABLE_PATH)
    public ResponseEntity<PageResponseDTO<BookResponseDTO>> findAvailableBooks(Pageable pageable){
        PageResponseDTO<BookResponseDTO> books = bookService.findAvailableBooks(pageable);
        return contentVerifier.verifyingContent(books);
    }

    @GetMapping(AppConstants.SEARCH_TITLE_PATH)
    public ResponseEntity<PageResponseDTO<BookResponseDTO>> findByTitle(Pageable pageable, @RequestParam String title){
        PageResponseDTO<BookResponseDTO> books = bookService.findByTitle(pageable, title);
        return contentVerifier.verifyingContent(books);
    }

    @GetMapping(AppConstants.SEARCH_TYPE_PATH)
    public ResponseEntity<PageResponseDTO<BookResponseDTO>> findByType(Pageable pageable, @RequestParam String type){
        PageResponseDTO<BookResponseDTO> books = bookService.findByType(pageable, type);
        return contentVerifier.verifyingContent(books);
    }
}
