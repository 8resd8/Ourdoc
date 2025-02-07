package com.ssafy.ourdoc.domain.book.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.HomeworkRequest;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.book.repository.HomeworkRepository;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeworkService {

	private final HomeworkRepository homeworkRepository;
	private final BookRepository bookRepository;
	private final TeacherClassRepository teacherClassRepository;

	public void addHomework(HomeworkRequest request, User user) {
		if (user.getUserType().equals(UserType.학생)) {
			throw new ForbiddenException("숙제도서를 생성할 권한이 없습니다.");
		}
		Book book = bookRepository.findById(request.bookId())
			.orElseThrow(() -> new NoSuchElementException("해당하는 ID의 도서가 없습니다."));
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성).getClassRoom();
		if (homeworkRepository.existsByBookAndUserAndClassRoom(book, user, classRoom)) {
			throw new IllegalArgumentException("이미 숙제 도서로 등록했습니다.");
		}
		Homework homework = Homework.builder().book(book).user(user).classRoom(classRoom).build();
		homeworkRepository.save(homework);
	}

	public void deleteHomework(HomeworkRequest request, User user) {
		if (user.getUserType().equals(UserType.학생)) {
			throw new ForbiddenException("숙제도서를 삭제할 권한이 없습니다.");
		}
		Book book = bookRepository.findById(request.bookId())
			.orElseThrow(() -> new NoSuchElementException("해당하는 ID의 도서가 없습니다."));
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성).getClassRoom();
		Homework homework = homeworkRepository.findByBookAndUserAndClassRoom(book, user, classRoom)
			.orElseThrow(() -> new IllegalArgumentException("숙제 도서로 등록한 도서가 아닙니다."));
		homeworkRepository.delete(homework);
	}
}
