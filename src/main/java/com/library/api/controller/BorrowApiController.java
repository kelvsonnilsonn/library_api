package com.library.api.controller;

import com.library.api.dto.borrow.BorrowRequestDTO;
import com.library.api.dto.borrow.BorrowResponseDTO;
import com.library.api.service.BorrowService;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.BORROW_BASE_PATH)
@RequiredArgsConstructor
public class BorrowApiController implements BorrowAPI {

    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<BorrowResponseDTO> borrow(@RequestBody BorrowRequestDTO borrowRequestDTO){
        return ResponseEntity.ok(borrowService.borrowBook(borrowRequestDTO));
    }

    @PostMapping(AppConstants.ID_PATH)
    public ResponseEntity<BorrowResponseDTO> returnBook(@PathVariable Long id){
        return ResponseEntity.ok(borrowService.returnBook(id));
    }
}
