package com.library.api.controller;

import com.library.api.dto.books.BookRequestDTO;
import com.library.api.dto.books.BookResponseDTO;
import com.library.api.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Livros", description = "Gestão de livros")
public interface BookAPI {

    @Operation(summary = "Criar um livro", description = "Criar um novo livro")
    @ApiResponse(responseCode = HttpConstants.CREATED, description = "Livro criado com sucesso")
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = HttpConstants.BAD_REQUEST_MSG)
    @ApiResponse(responseCode = HttpConstants.CONFLICT, description = "O ISBN inserido já existe")
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<BookResponseDTO> create(@RequestBody BookRequestDTO bookRequestDTO);

    @Operation(summary = "Deletar um livro", description = "Deleta um livro")
    @ApiResponse(responseCode = HttpConstants.CREATED, description = "Livro deletado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<String> delete(@PathVariable Long id);

    @Operation(summary = "Procurar um livro", description = "Procura um livro com ID")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Livro encontrado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<BookResponseDTO> findById(@PathVariable Long id);

    @Operation(summary = "Procurar um livro", description = "Procurar um livro com ISBN")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Livro encontrado com sucesso")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = HttpConstants.NOT_FOUND_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<BookResponseDTO> findByIsbn(@PathVariable String isbn);
}
