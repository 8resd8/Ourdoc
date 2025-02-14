package com.ssafy.ourdoc.domain.book.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.book.dto.most.BookMostDto;
import com.ssafy.ourdoc.domain.book.entity.Book;

public interface BookQueryRepository {
	Page<Book> findBookPage(String title, String author, String publisher, Pageable pageable);

	List<Book> findBookList(String title, String author, String publisher);

	BookMostDto findBookGradeMost(Long userId);

	BookMostDto findBookClassMost(Long userId);
}
