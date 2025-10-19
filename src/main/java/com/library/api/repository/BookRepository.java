package com.library.api.repository;

import com.library.api.enums.BookType;
import com.library.api.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.isbn.number = :isbn")
    Optional<Book> findByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Book> findByTitle(Pageable pageable, String title);

    @Query("SELECT b FROM Book b WHERE b.type = :type")
    Page<Book> findByType(Pageable pageable, BookType type);

    @Query("SELECT b FROM Book b WHERE NOT EXISTS (SELECT 1 FROM Borrow br WHERE br.book.id = b.id AND br.returnDate IS NULL)")
    Page<Book> findAvailableBooks(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.id = :bookId AND b.author.id = :authorId")
    Optional<Book> findByIdAndAuthorId(Long bookId, Long authorId);
}
