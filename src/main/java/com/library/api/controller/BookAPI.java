package com.library.api.controller;

import com.library.api.dto.BookRequestDTO;
import com.library.api.dto.BookResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface BookAPI {
    ResponseEntity<BookResponseDTO> create(@RequestBody BookRequestDTO bookRequestDTO);
    ResponseEntity<String> delete(@RequestParam Long id);
    ResponseEntity<BookResponseDTO> findById(@RequestParam Long id);
    ResponseEntity<BookResponseDTO> findByIsbn(@RequestParam String isbn);
}
