package com.library.api.repository;

import com.library.api.model.Book;
import com.library.api.model.Borrow;
import com.library.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    boolean existsByBookAndReturnDateIsNull(Book book);
    Optional<Borrow> findByBookIdAndUserAndReturnDateIsNull(Long bookId, User user);
    Page<Borrow> findByUserAndReturnDateIsNull(Pageable pageable, User user);

    @Query("SELECT b FROM Borrow b WHERE b.user = :user AND b.returnDate IS NULL AND b.dueDate < :currentDate")
    Page<Borrow> findOverdueByUser(Pageable pageable, User user, LocalDateTime currentDate);

    @Query("SELECT b FROM Borrow b WHERE b.user = :user")
    Page<Borrow> getUserBorrowHistory(Pageable pageable, User user);
}
