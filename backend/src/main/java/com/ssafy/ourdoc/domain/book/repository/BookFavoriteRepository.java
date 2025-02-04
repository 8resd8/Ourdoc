package com.ssafy.ourdoc.domain.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;
import com.ssafy.ourdoc.domain.user.entity.User;

public interface BookFavoriteRepository extends JpaRepository<BookFavorite, Long> {
	Optional<BookFavorite> findByBookAndUser(Book book, User user);

	boolean existsByBookAndUser(Book book, User user);

	List<BookFavorite> findByUser(User user);
}
