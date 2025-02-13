package com.ssafy.ourdoc.domain.book.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.dto.recommend.BookRecommendDetailStudent;
import com.ssafy.ourdoc.domain.book.dto.recommend.BookRecommendDetailTeacher;
import com.ssafy.ourdoc.domain.book.dto.recommend.BookRecommendResponseStudent;
import com.ssafy.ourdoc.domain.book.dto.recommend.BookRecommendResponseTeacher;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookRecommend;
import com.ssafy.ourdoc.domain.book.repository.BookRecommendRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.classroom.service.ClassService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookRecommendService {
	private final BookRepository bookRepository;
	private final BookRecommendRepository bookRecommendRepository;
	private final TeacherClassRepository teacherClassRepository;
	private final StudentClassRepository studentClassRepository;
	private final ClassRoomRepository classRoomRepository;
	private final BookReportRepository bookReportRepository;
	private final BookService bookService;
	private final ClassService classService;

	public void addBookRecommend(BookRequest request, User user) {
		if (user.getUserType().equals(UserType.학생)) {
			throw new ForbiddenException("추천도서를 생성할 권한이 없습니다.");
		}
		Book book = bookService.findBookById(request.bookId());
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

	public void deleteBookRecommend(BookRequest request, User user) {
		if (user.getUserType().equals(UserType.학생)) {
			throw new ForbiddenException("추천도서를 삭제할 권한이 없습니다.");
		}
		Book book = bookService.findBookById(request.bookId());
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성)
			.map(TeacherClass::getClassRoom)
			.orElseThrow(() -> new NoSuchElementException("활성 상태의 교사 학급 정보가 존재하지 않습니다."));
		BookRecommend bookRecommend = bookRecommendRepository.findByBookAndUserAndClassRoom(book, user, classRoom)
			.orElseThrow(() -> new IllegalArgumentException("추천 도서로 등록한 도서가 아닙니다."));
		bookRecommendRepository.delete(bookRecommend);
	}

	public BookRecommendResponseTeacher getBookRecommendsTeacher(BookSearchRequest request, User user,
		Pageable pageable) {
		ClassRoom userClassRoom = classService.getUserClassRoom(user);
		Long schoolId = userClassRoom.getSchool().getId();
		int grade = userClassRoom.getGrade();
		int studentCount = studentClassRepository.countByClassRoom(userClassRoom);

		List<ClassRoom> sameGradeClass = classRoomRepository.findActiveClassBySchoolAndGrade(schoolId, grade);
		List<Book> searchedBooks = bookRepository.findBookList(request.title(), request.author(), request.publisher());
		Page<BookRecommend> bookRecommends = bookRecommendRepository.findByClassRoomInAndBookIn(sameGradeClass,
			searchedBooks, pageable);

		List<BookRecommendDetailTeacher> details = bookRecommends.stream()
			.map(bookRecommend -> toBookRecommendDetailTeacher(bookRecommend, user.getId()))
			.toList();

		Page<BookRecommendDetailTeacher> content = new PageImpl<>(details, pageable, details.size());
		return new BookRecommendResponseTeacher(studentCount, content);
	}

	public BookRecommendResponseTeacher getBookRecommendsTeacherClass(BookSearchRequest request, User user,
		Pageable pageable) {
		ClassRoom userClassRoom = classService.getUserClassRoom(user);
		int studentCount = studentClassRepository.countByClassRoom(userClassRoom);

		List<Book> searchedBooks = bookRepository.findBookList(request.title(), request.author(), request.publisher());
		Page<BookRecommend> bookRecommends = bookRecommendRepository.findByClassRoomAndBookIn(userClassRoom,
			searchedBooks, pageable);

		List<BookRecommendDetailTeacher> details = bookRecommends.stream()
			.map(bookRecommend -> toBookRecommendDetailTeacher(bookRecommend, user.getId()))
			.toList();

		Page<BookRecommendDetailTeacher> content = new PageImpl<>(details, pageable, details.size());
		return new BookRecommendResponseTeacher(studentCount, content);
	}

	public BookRecommendResponseStudent getBookRecommendsStudent(BookSearchRequest request, User user,
		Pageable pageable) {
		ClassRoom userClassRoom = classService.getUserClassRoom(user);
		Long schoolId = userClassRoom.getSchool().getId();
		int grade = userClassRoom.getGrade();

		List<ClassRoom> sameGradeClass = classRoomRepository.findActiveClassBySchoolAndGrade(schoolId, grade);
		List<Book> searchedBooks = bookRepository.findBookList(request.title(), request.author(), request.publisher());
		Page<BookRecommend> bookRecommends = bookRecommendRepository.findByClassRoomInAndBookIn(sameGradeClass,
			searchedBooks, pageable);

		List<BookRecommendDetailStudent> details = bookRecommends.stream()
			.map(bookRecommend -> toBookRecommendDetailStudent(bookRecommend, user.getId()))
			.toList();

		Page<BookRecommendDetailStudent> content = new PageImpl<>(details, pageable, details.size());
		return new BookRecommendResponseStudent(content);
	}

	public BookRecommendResponseStudent getBookRecommendsStudentClass(BookSearchRequest request, User user,
		Pageable pageable) {
		ClassRoom userClassRoom = classService.getUserClassRoom(user);

		List<Book> searchedBooks = bookRepository.findBookList(request.title(), request.author(), request.publisher());
		Page<BookRecommend> bookRecommends = bookRecommendRepository.findByClassRoomAndBookIn(userClassRoom,
			searchedBooks, pageable);

		List<BookRecommendDetailStudent> details = bookRecommends.stream()
			.map(bookRecommend -> toBookRecommendDetailStudent(bookRecommend, user.getId()))
			.toList();
		Page<BookRecommendDetailStudent> content = new PageImpl<>(details, pageable, details.size());
		return new BookRecommendResponseStudent(content);
	}

	private BookRecommendDetailTeacher toBookRecommendDetailTeacher(BookRecommend bookRecommend, Long userId) {
		Long bookId = bookRecommend.getBook().getId();
		int submitCount = bookReportRepository.countByUserIdAndBookId(userId, bookId);
		return BookRecommendDetailTeacher.of(bookRecommend, submitCount);
	}

	private BookRecommendDetailStudent toBookRecommendDetailStudent(BookRecommend bookRecommend, Long userId) {
		Long bookId = bookRecommend.getBook().getId();
		boolean submitStatus = bookReportRepository.countByUserIdAndBookId(userId, bookId) > 0;
		return BookRecommendDetailStudent.of(bookRecommend, submitStatus);
	}
}
