package com.library.api.service;

import com.library.api.dto.EventIntervalDTO;
import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.UserEventsIntervalDTO;
import com.library.api.event.EventStore;
import com.library.api.exception.InvalidDateIntervalException;
import com.library.api.exception.UserNotFoundException;
import com.library.api.repository.EventStoreRepository;
import com.library.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventStoreService {

    private final EventStoreRepository eventStoreRepository;
    private final UserRepository userRepository;
    private final AuthenticationInformation authenticationInformation;

    public void saveEvent(String aggregateType, Long aggregateId, Object event){
        EventStore eventStore = new EventStore(aggregateType, aggregateId, event.getClass().getSimpleName(), event);
        eventStoreRepository.save(eventStore);
    }

    /**
     * PROCURA TODOS OS EVENTOS DO SISTEMA
     * */

    public PageResponseDTO<EventStore> findAllEvents(Pageable pageable){
        Page<EventStore> page = eventStoreRepository.findAll(pageable);
        return PageResponseDTO.fromPage(page);
    }

    /**
     * PROCURA EVENTOS DE UM DETERMINADO USUÁRIO
     * */

    public PageResponseDTO<EventStore> findAllByUserId(Pageable pageable, Long userId){
        Page<EventStore> page = eventStoreRepository.findAllEventsByUserId(pageable, userId);
        return PageResponseDTO.fromPage(page);
    }

    /**
     * PROCURA EVENTOS DE UM DETERMINADO PRODUTO
     * */

    public PageResponseDTO<EventStore> findByAggregateId(Pageable pageable, Long aggregateId){
        Page<EventStore> page = eventStoreRepository.findByAggregateId(pageable, aggregateId);
        return PageResponseDTO.fromPage(page);
    }

    /**
     * PROCURA EVENTOS DE UM DETERMINADO TIPO DE PRODUTOS
     * */

    public PageResponseDTO<EventStore> findByAggregateType(Pageable pageable, String type){
        Page<EventStore> page = eventStoreRepository.findByAggregateType(pageable, type);
        return PageResponseDTO.fromPage(page);
    }

    /**
     * PROCURA EVENTOS DO USUÁRIO LOGADO
     * */

    public PageResponseDTO<EventStore> findMyEvents(Pageable pageable){
        Page<EventStore> page = eventStoreRepository.findAllEventsByUserId(pageable, authenticationInformation.getAuthenticatedUser().getId());
        return PageResponseDTO.fromPage(page);
    }

    /**
     *  PROCURA TODOS OS EVENTOS EM UM DETERMINADO INTERVALO
     * */

    public PageResponseDTO<EventStore> findAllByInterval(Pageable pageable, EventIntervalDTO intervalDTO){
        validateInterval(intervalDTO.startDate(), intervalDTO.endDate());
        if(intervalDTO.endDate() != null && intervalDTO.startDate() != null){
            return findEventsInDate(pageable, intervalDTO.startDate(), intervalDTO.endDate());
        }

        if(intervalDTO.endDate() == null){
            return findEventsAfterDate(pageable, intervalDTO.startDate());
        }

        return findEventsBeforeDate(pageable, intervalDTO.endDate());
    }

    /**
     * PROCURA TODOS OS EVENTOS DE UM USUÁRIO EM UM DETERMINADO INTERVALO
     * */

    public PageResponseDTO<EventStore> findUserEventsByInterval(Pageable pageable, UserEventsIntervalDTO intervalDTO){
        Long userId = userRepository.findById(intervalDTO.userId()).orElseThrow(UserNotFoundException::new).getId();
        validateInterval(intervalDTO.startDate(), intervalDTO.endDate());

        if(intervalDTO.endDate() != null && intervalDTO.startDate() != null){
            return findEventsInDateWithUserId(pageable, intervalDTO.startDate(), intervalDTO.endDate(), userId);
        }

        if(intervalDTO.endDate() == null){
            return findEventsAfterDateWithUserId(pageable, intervalDTO.startDate(), userId);
        }

        return findEventsBeforeDateWithUserId(pageable, intervalDTO.endDate(), userId);
    }

    /**
     * PROCURA TODOS OS EVENTOS DO USUÁRIO LOGADO EM UM DETERMINADO INTERVALO
     * */

    public PageResponseDTO<EventStore> findMyEventsByInterval(Pageable pageable, EventIntervalDTO intervalDTO){
        Long userId = authenticationInformation.getAuthenticatedUser().getId();
        validateInterval(intervalDTO.startDate(), intervalDTO.endDate());

        if(intervalDTO.endDate() != null && intervalDTO.startDate() != null){
            return findEventsInDateWithUserId(pageable, intervalDTO.startDate(), intervalDTO.endDate(), userId);
        }

        if(intervalDTO.endDate() == null){
            return findEventsAfterDateWithUserId(pageable, intervalDTO.startDate(), userId);
        }

        return findEventsBeforeDateWithUserId(pageable, intervalDTO.endDate(), userId);
    }

    private PageResponseDTO<EventStore> findEventsAfterDateWithUserId(Pageable pageable, LocalDateTime startDate, Long userId){
        Page<EventStore> page = eventStoreRepository.findEventsAfterDateWithUserId(pageable, startDate, userId);
        return PageResponseDTO.fromPage(page);
    }

    private PageResponseDTO<EventStore> findEventsBeforeDateWithUserId(Pageable pageable, LocalDateTime date, Long userId){
        Page<EventStore> page = eventStoreRepository.findEventsBeforeDateWithUserId(pageable, date, userId);
        return PageResponseDTO.fromPage(page);
    }

    private PageResponseDTO<EventStore> findEventsInDateWithUserId(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate, Long userId){
        Page<EventStore> page = eventStoreRepository.findEventsInDateIntervalWithUserId(pageable, startDate, endDate, userId);
        return PageResponseDTO.fromPage(page);
    }

    private PageResponseDTO<EventStore> findEventsAfterDate(Pageable pageable, LocalDateTime startDate){
        Page<EventStore> page = eventStoreRepository.findEventsAfterDate(pageable, startDate);
        return PageResponseDTO.fromPage(page);
    }

    private PageResponseDTO<EventStore> findEventsBeforeDate(Pageable pageable, LocalDateTime date){
        Page<EventStore> page = eventStoreRepository.findEventsBeforeDate(pageable, date);
        return PageResponseDTO.fromPage(page);
    }

    private PageResponseDTO<EventStore> findEventsInDate(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate){
        Page<EventStore> page = eventStoreRepository.findEventsInDateInterval(pageable, startDate, endDate);
        return PageResponseDTO.fromPage(page);
    }

    private void validateInterval(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null && endDate == null) {
            throw new InvalidDateIntervalException();
        }
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new InvalidDateIntervalException();
        }
    }
}
