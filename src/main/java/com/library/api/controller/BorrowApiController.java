package com.library.api.controller;

import com.library.api.command.borrow.BorrowBookCommand;
import com.library.api.command.borrow.ReturnBookCommand;
import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.borrow.BorrowResponseDTO;
import com.library.api.service.command.BorrowCommandService;
import com.library.api.service.query.BorrowQueryService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BORROW_BASE_PATH)
@RequiredArgsConstructor
public class BorrowApiController implements BorrowAPI {

    private final BorrowCommandService commandService;
    private final BorrowQueryService queryService;
    private final ContentVerifier<BorrowResponseDTO> contentVerifier;

    @PostMapping
    public ResponseEntity<Void> borrow(@RequestBody BorrowBookCommand command){
        commandService.borrowBook(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping(AppConstants.ID_PATH)
    public ResponseEntity<Void> returnBook(@RequestBody ReturnBookCommand command){
        commandService.returnBook(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<BorrowResponseDTO>> findAllMyBorrows(Pageable pageable){
        PageResponseDTO<BorrowResponseDTO> borrows = queryService.findAllMyBorrows(pageable);
        return contentVerifier.verifyingContent(borrows);
    }

    @GetMapping(AppConstants.HISTORY_PATH)
    public ResponseEntity<PageResponseDTO<BorrowResponseDTO>> getBorrowHistory(Pageable pageable){
        PageResponseDTO<BorrowResponseDTO> borrows = queryService.getUserBorrowHistory(pageable);
        return contentVerifier.verifyingContent(borrows);
    }

    @GetMapping(AppConstants.DUE_PATH)
    public ResponseEntity<PageResponseDTO<BorrowResponseDTO>> findOverdueBorrows(Pageable pageable){
        PageResponseDTO<BorrowResponseDTO> borrows = queryService.findOverdueBorrows(pageable);
        return contentVerifier.verifyingContent(borrows);
    }
}
