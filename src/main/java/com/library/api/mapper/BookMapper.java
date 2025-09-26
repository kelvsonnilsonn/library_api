package com.library.api.mapper;

import com.library.api.dto.BookDTO;
import com.library.api.model.Book;
import com.library.api.model.User;

public class BookMapper {

    public static BookDTO bookToDTO(Book book){
        return new BookDTO(book.getTitle(), book.getIsbn(), book.getAuthorId(), book.getType());
    }

    public static Book dtoToBook(BookDTO bookDTO, User author){
        return new Book(author, bookDTO.type(), bookDTO.isbn(), bookDTO.title());
    }
}
