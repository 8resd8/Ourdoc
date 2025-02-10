package com.ssafy.ourdoc.domain.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;

public interface HomeworkRepository extends JpaRepository<Homework, Long>, HomeworkQueryRepository {
	Optional<Homework> findByBookAndUserAndClassRoom(Book book, User user, ClassRoom classRoom);

	boolean existsByBookAndUserAndClassRoom(Book book, User user, ClassRoom classRoom);
	
	List<Homework> findByClassRoomId(Long classRoomId);
}
