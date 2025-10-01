package com.library.api.service;

import com.library.api.dto.books.BookRequestDTO;
import com.library.api.dto.books.BookResponseDTO;
import com.library.api.dto.PageResponseDTO;
import com.library.api.enums.BookType;
import com.library.api.exception.BookNotFoundException;
import com.library.api.mapper.BookMapper;
import com.library.api.model.Book;
import com.library.api.model.User;
import com.library.api.repository.BookRepository;
import com.library.api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// V1.2

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserService userService;

    @Transactional
    public BookResponseDTO create(BookRequestDTO book){
        User author = userService.findEntityById(book.authorId());
        Book savedBook = bookRepository.save(BookMapper.dtoToBook(book, author));
        return BookMapper.toResponse(savedBook);
    }

    @Transactional
    public String delete(Long id) {
        Book book = findEntityById(id);
        bookRepository.deleteById(id);
        return String.format(AppConstants.BOOK_DELETED_MSG, book.getTitle());
    }

    @Transactional(readOnly = true)
    public BookResponseDTO findById(Long id){
        Book book = findEntityById(id);
        return BookMapper.toResponse(book);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<BookResponseDTO> findAll(Pageable pageable){
        Page<BookResponseDTO> books = bookRepository.findAll(pageable).map(BookMapper::toResponse);
        return PageResponseDTO.fromPage(books);
    }

    @Transactional(readOnly = true)
    public BookResponseDTO findByIsbn(String isbn){
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        return BookMapper.toResponse(book);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<BookResponseDTO> findByTitle(Pageable pageable, String title){
        Page<BookResponseDTO> books = bookRepository.findByTitle(pageable, title).map(BookMapper::toResponse);
        return PageResponseDTO.fromPage(books);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<BookResponseDTO> findByType(Pageable pageable, String type){
        BookType bookType = BookType.valueOf(type.toUpperCase());
        Page<BookResponseDTO> books = bookRepository.findByType(pageable, bookType).map(BookMapper::toResponse);
        return PageResponseDTO.fromPage(books);
    }

    @Transactional(readOnly = true)
    public Book findEntityById(Long id){
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }
}
