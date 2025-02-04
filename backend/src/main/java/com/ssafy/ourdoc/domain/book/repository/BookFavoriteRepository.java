package com.ssafy.ourdoc.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;
import com.ssafy.ourdoc.domain.user.entity.User;

public interface BookFavoriteRepository extends JpaRepository<BookFavorite, Long> {
	BookFavorite findBookFavoriteByBookAndUser(Book book, User user);
}
