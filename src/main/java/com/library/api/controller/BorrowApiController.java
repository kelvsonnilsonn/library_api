package com.library.api.controller;

import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.borrow.BorrowRequestDTO;
import com.library.api.dto.borrow.BorrowResponseDTO;
import com.library.api.service.BorrowService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BORROW_BASE_PATH)
@RequiredArgsConstructor
public class BorrowApiController implements BorrowAPI {

    private final BorrowService borrowService;
    private final ContentVerifier<BorrowResponseDTO> contentVerifier;

    @PostMapping
    public ResponseEntity<BorrowResponseDTO> borrow(@RequestBody BorrowRequestDTO borrowRequestDTO){
        return ResponseEntity.ok(borrowService.borrowBook(borrowRequestDTO));
    }

    @PostMapping(AppConstants.ID_PATH)
    public ResponseEntity<BorrowResponseDTO> returnBook(@PathVariable Long id){
        return ResponseEntity.ok(borrowService.returnBook(id));
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<BorrowResponseDTO>> findAllMyBorrows(Pageable pageable){
        PageResponseDTO<BorrowResponseDTO> borrows = borrowService.findAllMyBorrows(pageable);
        return contentVerifier.verifyingContent(borrows);
    }

    @GetMapping(AppConstants.DUE_PATH)
    public ResponseEntity<PageResponseDTO<BorrowResponseDTO>> findOverdueBorrows(Pageable pageable){
        PageResponseDTO<BorrowResponseDTO> borrows = borrowService.findOverdueBorrows(pageable);
        return contentVerifier.verifyingContent(borrows);
    }
}
