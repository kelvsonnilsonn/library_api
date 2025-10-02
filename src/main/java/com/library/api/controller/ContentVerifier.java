package com.library.api.controller;

import com.library.api.dto.PageResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
class ContentVerifier <T> {
    public ResponseEntity<PageResponseDTO<T>> verifyingContent(PageResponseDTO<T> content){
        if(content.content().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(content);
    }
}
