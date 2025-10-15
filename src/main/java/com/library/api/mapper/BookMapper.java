package com.library.api.mapper;

import com.library.api.command.book.CreateBookCommand;
import com.library.api.dto.books.BookRequestDTO;
import com.library.api.dto.books.BookResponseDTO;
import com.library.api.model.Book;
import com.library.api.model.User;

public class BookMapper {
    public static Book dtoToBook(CreateBookCommand command, User author){
        return new Book(author, command.type(), command.isbn(), command.title());
    }

    public static BookResponseDTO toResponse(Book book){
        return new BookResponseDTO(book.getId(), book.getTitle(), book.getIsbn(), book.getAuthorId(), book.getType(), book.getCreatedAt());
    }
}
