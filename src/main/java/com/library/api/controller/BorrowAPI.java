package com.library.api.controller;

import com.library.api.command.borrow.BorrowBookCommand;
import com.library.api.command.borrow.ReturnBookCommand;
import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.borrow.BorrowResponseDTO;
import com.library.api.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Empréstimos", description = "Operações de empréstimo e devolução")
public interface BorrowAPI {

    @Operation(summary = "Realizar empréstimo", description = "Realiza um empréstimo")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Empréstimo encontrado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<Void> borrow(@RequestBody BorrowBookCommand command);

    @Operation(summary = "Devolver um livro", description = "Devolve um livro emprestado")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Devolução realizada com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<Void> returnBook(@RequestBody ReturnBookCommand command);

    @Operation(summary = "Listar empréstimos", description = "Lista emprestismos de um usuário")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Listagem realizada com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<PageResponseDTO<BorrowResponseDTO>> findAllMyBorrows(Pageable pageable);

    @Operation(summary = "Listar atrasos", description = "Lista emprestismos atrasados de um usuário")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Listagem realizada com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<PageResponseDTO<BorrowResponseDTO>> findOverdueBorrows(Pageable pageable);

    @Operation(summary = "Listar histórico", description = "Lista histórico de empréstimos de um usuário")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Listagem realizada com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<PageResponseDTO<BorrowResponseDTO>> getBorrowHistory(Pageable pageable);
}
