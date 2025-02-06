package com.ssafy.ourdoc.domain.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;

public interface BookRecommendRepository extends JpaRepository<BookRecommend, Long>, BookQueryRepository {
	Optional<BookRecommend> findByBookAndUserAndClassRoom(Book book, User user, ClassRoom classRoom);

	boolean existsByBookAndUserAndClassRoom(Book book, User user, ClassRoom classRoom);
}
