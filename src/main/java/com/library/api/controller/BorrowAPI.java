package com.library.api.controller;

import com.library.api.dto.borrow.BorrowRequestDTO;
import com.library.api.dto.borrow.BorrowResponseDTO;
import com.library.api.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.mapstruct.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Mapper(componentModel = "spring")
public interface BorrowAPI {

    @Operation(summary = "Realizar empréstimo", description = "Realiza um empréstimo")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Empréstimo encontrado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<BorrowResponseDTO> borrow(@RequestBody BorrowRequestDTO borrowRequestDTO);

    @Operation(summary = "Devolver um livro", description = "Devolve um livro emprestado")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Devolução realizada com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<BorrowResponseDTO> returnBook(@PathVariable Long id);
}
