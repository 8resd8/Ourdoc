package com.ssafy.ourdoc.domain.book.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookRecommendDetailStudent;
import com.ssafy.ourdoc.domain.book.dto.BookRecommendDetailTeacher;
import com.ssafy.ourdoc.domain.book.dto.BookRecommendRequest;
import com.ssafy.ourdoc.domain.book.dto.BookRecommendResponseStudent;
import com.ssafy.ourdoc.domain.book.dto.BookRecommendResponseTeacher;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;
import com.ssafy.ourdoc.domain.book.repository.BookRecommendRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
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
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성)
			.map(TeacherClass::getClassRoom)
			.orElseThrow(() -> new NoSuchElementException("활성 상태의 교사 학급 정보가 존재하지 않습니다."));
		;
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
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성)
			.map(TeacherClass::getClassRoom)
			.orElseThrow(() -> new NoSuchElementException("활성 상태의 교사 학급 정보가 존재하지 않습니다."));
		BookRecommend bookRecommend = bookRecommendRepository.findByBookAndUserAndClassRoom(book, user, classRoom)
			.orElseThrow(() -> new IllegalArgumentException("추천 도서로 등록한 도서가 아닙니다."));
		bookRecommendRepository.delete(bookRecommend);
	}

	public BookRecommendResponseTeacher getBookRecommendsTeacher(User user) {
		ClassRoom userClassRoom = getUserClassRoom(user);
		Long schoolId = userClassRoom.getSchool().getId();
		int grade = userClassRoom.getGrade();
		int studentCount = studentClassRepository.countByClassRoom(userClassRoom);

		List<ClassRoom> sameGradeClass = classRoomRepository.findActiveClassBySchoolAndGrade(schoolId, grade);

		List<BookRecommend> bookRecommends = bookRecommendRepository.findByClassRoomIn(sameGradeClass);

		int submitCount = 0; // 독서록 제출 개수
		List<BookRecommendDetailTeacher> details = bookRecommends.stream()
			.map(bookRecommend -> BookRecommendDetailTeacher.of(bookRecommend.getBook(), bookRecommend, submitCount))
			.collect(Collectors.toList());

		return new BookRecommendResponseTeacher(studentCount, details);
	}

	public BookRecommendResponseStudent getBookRecommendsStudent(User user) {
		ClassRoom userClassRoom = getUserClassRoom(user);
		Long schoolId = userClassRoom.getSchool().getId();
		int grade = userClassRoom.getGrade();

		List<ClassRoom> sameGradeClass = classRoomRepository.findActiveClassBySchoolAndGrade(schoolId, grade);

		List<BookRecommend> bookRecommends = bookRecommendRepository.findByClassRoomIn(sameGradeClass);

		boolean submitStatus = true; // 독서록 제출 여부
		List<BookRecommendDetailStudent> details = bookRecommends.stream()
			.map(bookRecommend -> BookRecommendDetailStudent.of(bookRecommend.getBook(), bookRecommend, submitStatus))
			.collect(Collectors.toList());

		return new BookRecommendResponseStudent(details);
	}

	private ClassRoom getUserClassRoom(User user) {
		if (user.getUserType().equals(UserType.학생)) {
			return studentClassRepository.findByUserIdAndActive(user.getId(), Active.활성)
				.map(StudentClass::getClassRoom)
				.orElseThrow(() -> new NoSuchElementException("활성 상태의 학생 학급 정보가 존재하지 않습니다."));
		}
		if (user.getUserType().equals(UserType.교사)) {
			return teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성)
				.map(TeacherClass::getClassRoom)
				.orElseThrow(() -> new NoSuchElementException("활성 상태의 교사 학급 정보가 존재하지 않습니다."));
		}
		throw new NoSuchElementException("현재 유효한 상태의 학급 정보가 없습니다.");
	}
}
