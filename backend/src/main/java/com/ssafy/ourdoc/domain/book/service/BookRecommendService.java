package com.ssafy.ourdoc.domain.book.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookRecommendRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;
import com.ssafy.ourdoc.domain.book.repository.BookRecommendRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookRecommendService {
	private final BookRepository bookRepository;
	private final BookRecommendRepository bookRecommendRepository;
	private final TeacherClassRepository teacherClassRepository;
	private final StudentClassRepository studentClassRepository;
	private final ClassRoomRepository classRoomRepository;

	public void addBookRecommend(BookRecommendRequest request, User user) {
		if (user.getUserType().equals(UserType.학생)) {
			throw new ForbiddenException("추천도서를 생성할 권한이 없습니다.");
		}
		Book book = bookRepository.findById(request.bookId())
			.orElseThrow(() -> new NoSuchElementException("해당하는 ID의 도서가 없습니다."));
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성).getClassRoom();
		if (bookRecommendRepository.existsByBookAndUserAndClassRoom(book, user, classRoom)) {
			throw new IllegalArgumentException("이미 추천 도서로 등록했습니다.");
		}
		BookRecommend bookRecommend = BookRecommend.builder().book(book).user(user).classRoom(classRoom).build();
		bookRecommendRepository.save(bookRecommend);
	}

	public void deleteBookRecommend(BookRecommendRequest request, User user) {
		if (user.getUserType().equals(UserType.학생)) {
			throw new ForbiddenException("추천도서를 삭제할 권한이 없습니다.");
		}
		Book book = bookRepository.findById(request.bookId())
			.orElseThrow(() -> new NoSuchElementException("해당하는 ID의 도서가 없습니다."));
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성).getClassRoom();
		BookRecommend bookRecommend = bookRecommendRepository.findByBookAndUserAndClassRoom(book, user, classRoom)
			.orElseThrow(() -> new IllegalArgumentException("추천 도서로 등록한 도서가 아닙니다."));
		bookRecommendRepository.delete(bookRecommend);
	}

	public List<BookResponse> getBookRecommends(User user) {
		ClassRoom userClassRoom = getUserClassRoom(user);
		if (userClassRoom == null) {
			throw new ForbiddenException("현재 학급 정보가 없습니다.");
		}

		Long schoolId = userClassRoom.getSchool().getId();
		int grade = userClassRoom.getGrade();
		List<ClassRoom> sameGradeClass = classRoomRepository.findActiveClassBySchoolAndGrade(schoolId, grade);

		List<BookRecommend> bookRecommends = bookRecommendRepository.findByClassRoomIn(sameGradeClass);
		List<Book> books = bookRecommends.stream().map(BookRecommend::getBook).toList();

		return books.stream().map(BookResponse::of).collect(Collectors.toList());
	}

	private ClassRoom getUserClassRoom(User user) {
		if (user.getUserType().equals(UserType.학생)) {
			return studentClassRepository.findByUserIdAndActive(user.getId(), Active.활성).getClassRoom();
		}
		if (user.getUserType().equals(UserType.교사)) {
			return teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성).getClassRoom();
		}
		return null;
	}

}
