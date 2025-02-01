package com.ssafy.ourdoc.domain.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByTitleAndAuthorAndPublisher(String title, String author, String publisher);
}
