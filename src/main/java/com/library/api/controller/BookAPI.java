package com.library.api.controller;

import com.library.api.command.book.CreateBookCommand;
import com.library.api.command.book.DeleteBookCommand;
import com.library.api.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Livros", description = "Gestão de livros")
public interface BookAPI {

    @Operation(summary = "Criar um livro", description = "Criar um novo livro")
    @ApiResponse(responseCode = HttpConstants.CREATED, description = "Livro criado com sucesso")
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = HttpConstants.BAD_REQUEST_MSG)
    @ApiResponse(responseCode = HttpConstants.CONFLICT, description = "O ISBN inserido já existe")
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<Long> create(@RequestBody CreateBookCommand command);

    @Operation(summary = "Deletar um livro", description = "Deleta um livro")
    @ApiResponse(responseCode = HttpConstants.CREATED, description = "Livro deletado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<Void> delete(@RequestBody DeleteBookCommand command);

    @Operation(summary = "Listar livros", description = "Lista todos os livros")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Livros listados com sucesso")
    @ApiResponse(responseCode = HttpConstants.NO_CONTENT, description = HttpConstants.NO_CONTENT_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    public ResponseEntity<?> findBook(Pageable pageable,
                                      @RequestParam(required = false) Long id,
                                      @RequestParam(required = false) String isbn,
                                      @RequestParam(required = false) String title,
                                      @RequestParam(required = false) String type);

}
