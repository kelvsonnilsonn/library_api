package com.library.api.controller;

import com.library.api.command.book.DeleteBookCommand;
import com.library.api.command.user.DeleteUserCommand;
import com.library.api.dto.EventIntervalDTO;
import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.UserEventsIntervalDTO;
import com.library.api.event.EventStore;
import com.library.api.service.EventStoreService;
import com.library.api.service.command.BookCommandService;
import com.library.api.service.command.UserCommandService;
import com.library.api.util.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.ADMIN_PATH)
@PreAuthorize(AppConstants.PRE_AUTHORIZE_ADMIN_REQUISITION)
public class AdminController implements AdminAPI {

    private final EventStoreService eventStoreService;
    private final UserCommandService userCommandService;
    private final BookCommandService bookCommandService;

    // SEÇÃO DE LIVROS

    @DeleteMapping(AppConstants.BOOK_BASE_PATH)
    public ResponseEntity<Void> deleteById(@RequestBody @Valid DeleteBookCommand command){
        bookCommandService.deleteByAdmin(command);
        return ResponseEntity.ok().build();
    }

    // SEÇÃO DE USUÁRIOS

    @DeleteMapping(AppConstants.USER_BASE_PATH)
    public ResponseEntity<Void> delete(@RequestBody @Valid DeleteUserCommand command) {
        userCommandService.delete(command);
        return ResponseEntity.ok().build();
    }

    // SEÇÃO DE EVENTOS

    @GetMapping(AppConstants.EVENT_BASE_PATH)
    public ResponseEntity<PageResponseDTO<EventStore>> findEvents(Pageable pageable,
                                                                  @RequestParam(required = false) Long userId,
                                                                  @RequestParam(required = false) Long aggregateId,
                                                                  @RequestParam(required = false) String aggregateType) {

        if(userId != null){
            return ResponseEntity.ok(eventStoreService.findAllByUserId(pageable, userId));
        }
        if(aggregateId != null){
            return ResponseEntity.ok(eventStoreService.findByAggregateId(pageable, aggregateId));
        }
        if(aggregateType != null){
            return ResponseEntity.ok(eventStoreService.findByAggregateType(pageable, aggregateType));
        }
        return ResponseEntity.ok(eventStoreService.findAllEvents(pageable));
    }

    @PostMapping(AppConstants.EVENT_BASE_PATH + AppConstants.EVENTS_IN_INTERVAL_PATH)
    public ResponseEntity<PageResponseDTO<EventStore>> findEventsByInterval(Pageable pageable, @RequestBody EventIntervalDTO intervalDTO) {
        return ResponseEntity.ok(eventStoreService.findAllByInterval(pageable, intervalDTO));
    }

    @PostMapping(AppConstants.EVENT_BASE_PATH + AppConstants.USER_EVENTS_IN_INTERVAL_PATH)
    public ResponseEntity<PageResponseDTO<EventStore>> findAllUserEventsInInterval(Pageable pageable, @RequestBody UserEventsIntervalDTO intervalDTO) {
        return ResponseEntity.ok(eventStoreService.findUserEventsByInterval(pageable, intervalDTO));
    }

}
