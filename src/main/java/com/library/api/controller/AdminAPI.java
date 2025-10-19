package com.library.api.controller;

import com.library.api.command.book.DeleteBookCommand;
import com.library.api.command.user.DeleteUserCommand;
import com.library.api.dto.EventIntervalDTO;
import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.UserEventsIntervalDTO;
import com.library.api.event.EventStore;
import com.library.api.util.HttpConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AdminAPI {

    // SEÇÃO DE LIVROS

    @Operation(summary = "Excluir livro", description = "Exclui um livro permanentemente do sistema (apenas administradores)")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Livro excluído com sucesso")
    @ApiResponse(responseCode = HttpConstants.UNAUTHORIZED, description = HttpConstants.UNAUTHORIZED_MSG)
    @ApiResponse(responseCode = HttpConstants.FORBIDDEN, description = HttpConstants.FORBIDDEN_MSG)
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = "Dados de exclusão inválidos")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = "Livro não encontrado")
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<Void> deleteById(@RequestBody @Valid DeleteBookCommand command);

    // SEÇÃO DE USUÁRIOS

    @Operation(summary = "Excluir usuário", description = "Exclui um usuário do sistema (apenas administradores)")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Usuário excluído com sucesso")
    @ApiResponse(responseCode = HttpConstants.UNAUTHORIZED, description = HttpConstants.UNAUTHORIZED_MSG)
    @ApiResponse(responseCode = HttpConstants.FORBIDDEN, description = HttpConstants.FORBIDDEN_MSG)
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = "Dados de exclusão inválidos")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = "Usuário não encontrado")
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<Void> delete(@RequestBody @Valid DeleteUserCommand command);

    @Operation(summary = "Buscar usuários", description = """
        Busca usuários por diferentes critérios.
        Prioridade: id > nome > todos.
        Exemplos:
        - ?id=123 → Usuário específico por ID
        - ?name=joao → Usuário por nome
        - Sem parâmetros → Todos usuários (paginação)
        """)
    @ApiResponse(responseCode = HttpConstants.OK, description = "Usuário(s) retornado(s) com sucesso")
    @ApiResponse(responseCode = HttpConstants.UNAUTHORIZED, description = HttpConstants.UNAUTHORIZED_MSG)
    @ApiResponse(responseCode = HttpConstants.FORBIDDEN, description = HttpConstants.FORBIDDEN_MSG)
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = "Parâmetros de busca inválidos")
    @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = "Usuário não encontrado")
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<?> findUser(Pageable pageable,
                               @RequestParam(required = false) Long id,
                               @RequestParam(required = false) String name);

    // SEÇÃO DE EVENTOS

    @Operation(summary = "Buscar eventos", description = """
        Busca eventos por diferentes critérios.
        Prioridade: userId > aggregateId > aggregateType > todos.
        Exemplos:
        - ?userId=456 → Eventos do usuário
        - ?aggregateId=789 → Eventos do agregado
        - ?aggregateType=ORDER → Eventos por tipo
        - Sem parâmetros → Todos eventos (paginação)
        """)
    @ApiResponse(responseCode = HttpConstants.OK, description = "Eventos retornados com sucesso")
    @ApiResponse(responseCode = HttpConstants.UNAUTHORIZED, description = HttpConstants.UNAUTHORIZED_MSG)
    @ApiResponse(responseCode = HttpConstants.FORBIDDEN, description = HttpConstants.FORBIDDEN_MSG)
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = "Parâmetros de filtro inválidos")
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<PageResponseDTO<EventStore>> findEvents(Pageable pageable,
                                                           @RequestParam(required = false) Long userId,
                                                           @RequestParam(required = false) Long aggregateId,
                                                           @RequestParam(required = false) String aggregateType);

    @Operation(summary = "Buscar eventos em intervalo", description = "Busca todos os eventos em um intervalo de datas específico")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Eventos do intervalo retornados com sucesso")
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = "Intervalo de datas inválido")
    @ApiResponse(responseCode = HttpConstants.UNAUTHORIZED, description = HttpConstants.UNAUTHORIZED_MSG)
    @ApiResponse(responseCode = HttpConstants.FORBIDDEN, description = HttpConstants.FORBIDDEN_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<PageResponseDTO<EventStore>> findEventsByInterval(Pageable pageable,
                                                                     @RequestBody @Valid EventIntervalDTO intervalDTO);

    @Operation(summary = "Buscar eventos de usuário em intervalo", description = "Busca eventos de um usuário específico em intervalo de datas")
    @ApiResponse(responseCode = HttpConstants.OK, description = "Eventos do usuário no intervalo retornados com sucesso")
    @ApiResponse(responseCode = HttpConstants.BAD_REQUEST, description = "Intervalo de datas inválido")
    @ApiResponse(responseCode = HttpConstants.UNAUTHORIZED, description = HttpConstants.UNAUTHORIZED_MSG)
    @ApiResponse(responseCode = HttpConstants.FORBIDDEN, description = HttpConstants.FORBIDDEN_MSG)
    @ApiResponse(responseCode = HttpConstants.SERVER_ERROR, description = HttpConstants.INTERN_SERVER_ERROR_MSG)
    ResponseEntity<PageResponseDTO<EventStore>> findAllUserEventsInInterval(Pageable pageable,
                                                                            @RequestBody @Valid UserEventsIntervalDTO intervalDTO);
}