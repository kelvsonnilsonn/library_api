package com.library.api.controller;

import com.library.api.command.book.CreateBookCommand;
import com.library.api.command.book.DeleteBookCommand;
import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.BookResponseDTO;
import com.library.api.service.command.BookCommandService;
import com.library.api.service.query.BookQueryService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BOOK_BASE_PATH)
@RequiredArgsConstructor
@PreAuthorize(AppConstants.PRE_AUTHORIZE_ALL_REQUISITION)
public class BookController implements BookAPI{

    private final BookCommandService commandService;
    private final BookQueryService queryService;
    private final ContentVerifier<BookResponseDTO> contentVerifier;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CreateBookCommand command){
        Long bookId = commandService.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookId);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody DeleteBookCommand command){
        commandService.delete(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> findBook(Pageable pageable,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String type){
        if(id != null){
            return ResponseEntity.ok(queryService.findById(id));
        }
        if(isbn != null){
            return ResponseEntity.ok(queryService.findByIsbn(isbn));
        }
        if(title != null){
            return ResponseEntity.ok(queryService.findByTitle(pageable, title));
        }
        if(type != null){
            return ResponseEntity.ok(queryService.findByType(pageable, type));
        }
        return contentVerifier.verifyingContent(queryService.findAll(pageable));
    }

    @GetMapping(AppConstants.SEARCH_AVAILABLE_PATH)
    public ResponseEntity<PageResponseDTO<BookResponseDTO>> findAvailableBooks(Pageable pageable){
        PageResponseDTO<BookResponseDTO> books = queryService.findAvailableBooks(pageable);
        return contentVerifier.verifyingContent(books);
    }
}
