package com.library.api.repository;

import com.library.api.model.Book;
import com.library.api.model.Borrow;
import com.library.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    boolean existsByBookAndReturnDateIsNull(Book book);
    Optional<Borrow> findByBookIdAndUserAndReturnDateIsNull(Long bookId, User user);
}
