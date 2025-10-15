package com.library.api.service.query;

import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.BorrowResponseDTO;
import com.library.api.mapper.BorrowMapper;
import com.library.api.model.Borrow;
import com.library.api.repository.BorrowRepository;
import com.library.api.service.AuthenticationInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BorrowQueryService {

    private final BorrowRepository borrowRepository;
    private final AuthenticationInformation authenticationInformation;
    private final BorrowMapper borrowMapper;

    public PageResponseDTO<BorrowResponseDTO> findAllMyBorrows(Pageable pageable){
        Page<Borrow> borrows = borrowRepository.findByUserAndReturnDateIsNull(pageable, authenticationInformation.getAuthenticatedUser());
        Page<BorrowResponseDTO> page = borrows.map(borrowMapper::toResponse);
        return PageResponseDTO.fromPage(page);
    }

    public PageResponseDTO<BorrowResponseDTO> findOverdueBorrows(Pageable pageable){
        Page<Borrow> borrows = borrowRepository.findOverdueByUser(pageable, authenticationInformation.getAuthenticatedUser(), LocalDateTime.now());
        Page<BorrowResponseDTO> page = borrows.map(borrowMapper::toResponse);
        return PageResponseDTO.fromPage(page);
    }

    public PageResponseDTO<BorrowResponseDTO> getUserBorrowHistory(Pageable pageable){
        Page<BorrowResponseDTO> page = borrowRepository.getUserBorrowHistory(pageable, authenticationInformation.getAuthenticatedUser()).map(borrowMapper::toResponse);
        return PageResponseDTO.fromPage(page);
    }
}
