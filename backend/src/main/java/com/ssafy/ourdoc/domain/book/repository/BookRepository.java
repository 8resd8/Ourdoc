package com.ssafy.ourdoc.domain.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>, BookQueryRepository {
	Optional<Book> findByIsbn(String isbn);

	List<Book> findByIsbnIn(List<String> isbn);
}
