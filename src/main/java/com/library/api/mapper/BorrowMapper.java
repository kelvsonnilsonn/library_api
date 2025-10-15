package com.library.api.mapper;

import com.library.api.dto.BorrowResponseDTO;
import com.library.api.model.Borrow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "returned", expression = "java(borrow.isReturned())")
    BorrowResponseDTO toResponse(Borrow borrow);
}
