package com.library.api.service.query;

import com.library.api.dto.PageResponseDTO;
import com.library.api.dto.BookResponseDTO;
import com.library.api.enums.BookType;
import com.library.api.exception.BookNotFoundException;
import com.library.api.mapper.BookMapper;
import com.library.api.model.Book;
import com.library.api.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookQueryService {

    private final BookRepository bookRepository;

    public BookResponseDTO findById(Long id){
        Book book = findEntityById(id);
        return BookMapper.toResponse(book);
    }

    public PageResponseDTO<BookResponseDTO> findAll(Pageable pageable){
        Page<BookResponseDTO> books = bookRepository.findAll(pageable).map(BookMapper::toResponse);
        return PageResponseDTO.fromPage(books);
    }

    public BookResponseDTO findByIsbn(String isbn){
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        return BookMapper.toResponse(book);
    }

    public PageResponseDTO<BookResponseDTO> findByTitle(Pageable pageable, String title){
        Page<BookResponseDTO> books = bookRepository.findByTitle(pageable, title).map(BookMapper::toResponse);
        return PageResponseDTO.fromPage(books);
    }

    public PageResponseDTO<BookResponseDTO> findByType(Pageable pageable, String type){
        BookType bookType = BookType.valueOf(type.toUpperCase());
        Page<BookResponseDTO> books = bookRepository.findByType(pageable, bookType).map(BookMapper::toResponse);
        return PageResponseDTO.fromPage(books);
    }

    public PageResponseDTO<BookResponseDTO> findAvailableBooks(Pageable pageable){
        Page<BookResponseDTO> books = bookRepository.findAvailableBooks(pageable).map(BookMapper::toResponse);
        return PageResponseDTO.fromPage(books);
    }

    public Book findEntityById(Long id){
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }
}
