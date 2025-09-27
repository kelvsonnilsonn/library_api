package com.library.api.mapper;

import com.library.api.dto.BookRequestDTO;
import com.library.api.dto.BookResponseDTO;
import com.library.api.model.Book;
import com.library.api.model.User;

public class BookMapper {
    public static Book dtoToBook(BookRequestDTO bookRequestDTO, User author){
        return new Book(author, bookRequestDTO.type(), bookRequestDTO.isbn(), bookRequestDTO.title());
    }

    public static BookResponseDTO toResponse(Book book){
        return new BookResponseDTO(book.getTitle(), book.getIsbn(), book.getAuthorId(), book.getType(), book.getCreatedAt());
    }
}
