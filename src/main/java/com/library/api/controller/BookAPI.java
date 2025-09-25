package com.library.api.controller;

import com.library.api.dto.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface BookAPI {
    ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO);
    ResponseEntity<String> delete(@RequestParam Long id);
    ResponseEntity<BookDTO> findById(@RequestParam Long id);
}
