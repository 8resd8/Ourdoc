package com.ssafy.ourdoc.domain.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;

public interface BookRecommendRepository extends JpaRepository<BookRecommend, Long>, BookQueryRepository {
	Optional<BookRecommend> findByBookAndUserAndClassRoom(Book book, User user, ClassRoom classRoom);

	boolean existsByBookAndUserAndClassRoom(Book book, User user, ClassRoom classRoom);

	List<BookRecommend> findByClassRoom(ClassRoom classRoom);

	Page<BookRecommend> findByClassRoom(ClassRoom classRoom, Pageable pageable);

	List<BookRecommend> findByClassRoomIn(List<ClassRoom> classRooms);

	Page<BookRecommend> findByClassRoomIn(List<ClassRoom> classRooms, Pageable pageable);
}
