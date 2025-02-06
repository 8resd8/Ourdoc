package com.ssafy.ourdoc.domain.book.repository;

import java.util.List;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;
import com.ssafy.ourdoc.domain.user.entity.User;

public interface BookQueryRepository {
	List<Book> findBookList(String title, String author, String publisher);

	List<BookRecommend> findRecommendBookList(User user);
}
