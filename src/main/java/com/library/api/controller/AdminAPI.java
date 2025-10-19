package com.library.api.controller;

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
