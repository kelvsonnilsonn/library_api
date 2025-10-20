package com.library.api.dto;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

public record PageResponseDTO <T>(
    List<T> content,
    int actualPage,
    int totalPages,
    long totalItems,
    int itemsShowedInPage,
    int httpStatus
) implements Serializable {

    public static <T> PageResponseDTO<T> fromPage(Page<T> page){
        return new PageResponseDTO<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                HttpStatus.OK.value()
        );
    }
}
