package com.ssafy.ourdoc.domain.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.ourdoc.domain.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	@Query("SELECT b FROM Book b WHERE (:title is null or b.title = :title)"
		+ "and (:author is null or b.author = :author)"
		+ "and (:publisher is null or b.publisher = :publisher)")
	List<Book> findByTitleAndAuthorAndPublisher(String title, String author, String publisher);
}
