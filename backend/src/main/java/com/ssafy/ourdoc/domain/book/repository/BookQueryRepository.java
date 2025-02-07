package com.ssafy.ourdoc.domain.book.repository;

import java.util.List;

import com.ssafy.ourdoc.domain.book.entity.Book;

public interface BookQueryRepository {
	List<Book> findBookList(String title, String author, String publisher);
}
