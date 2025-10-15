package com.library.api.controller;

import com.library.api.command.book.CreateBookCommand;
import com.library.api.command.book.DeleteBookCommand;
import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.books.BookResponseDTO;
import com.library.api.service.command.BookCommandService;
import com.library.api.service.query.BookQueryService;
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

    private final BookCommandService commandService;
    private final BookQueryService queryService;
    private final ContentVerifier<BookResponseDTO> contentVerifier;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CreateBookCommand command){
        Long bookId = commandService.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookId);
    }

    @DeleteMapping(AppConstants.ID_PATH)
    public ResponseEntity<Void> delete(@RequestBody DeleteBookCommand command){
        commandService.delete(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping(AppConstants.ID_PATH)
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Long id) {
        BookResponseDTO response = queryService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(AppConstants.SEARCH_ISBN_PATH)
    public ResponseEntity<BookResponseDTO> findByIsbn(@PathVariable String isbn) {
        BookResponseDTO response = queryService.findByIsbn(isbn);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<BookResponseDTO>> findAll(Pageable pageable){
        PageResponseDTO<BookResponseDTO> books = queryService.findAll(pageable);
        return contentVerifier.verifyingContent(books);
    }

    @GetMapping(AppConstants.SEARCH_AVAILABLE_PATH)
    public ResponseEntity<PageResponseDTO<BookResponseDTO>> findAvailableBooks(Pageable pageable){
        PageResponseDTO<BookResponseDTO> books = queryService.findAvailableBooks(pageable);
        return contentVerifier.verifyingContent(books);
    }

    @GetMapping(AppConstants.SEARCH_TITLE_PATH)
    public ResponseEntity<PageResponseDTO<BookResponseDTO>> findByTitle(Pageable pageable, @RequestParam String title){
        PageResponseDTO<BookResponseDTO> books = queryService.findByTitle(pageable, title);
        return contentVerifier.verifyingContent(books);
    }

    @GetMapping(AppConstants.SEARCH_TYPE_PATH)
    public ResponseEntity<PageResponseDTO<BookResponseDTO>> findByType(Pageable pageable, @RequestParam String type){
        PageResponseDTO<BookResponseDTO> books = queryService.findByType(pageable, type);
        return contentVerifier.verifyingContent(books);
    }
}
