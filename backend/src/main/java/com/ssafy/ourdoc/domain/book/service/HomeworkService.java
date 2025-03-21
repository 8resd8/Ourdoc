package com.ssafy.ourdoc.domain.book.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkStudentDetail;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkStudentDetailPage;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkStudentResponse;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkTeacherDetail;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkTeacherDetailPage;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkTeacherResponse;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.book.repository.BookRecommendRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.book.repository.HomeworkRepository;
import com.ssafy.ourdoc.domain.book.util.BookStatusMapper;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStudent;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.BookReportTeacher;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.bookreport.service.BookReportStudentService;
import com.ssafy.ourdoc.domain.bookreport.service.BookReportTeacherService;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.service.ClassService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.AuthStatus;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeworkService {

	private final HomeworkRepository homeworkRepository;
	private final BookRepository bookRepository;
	private final BookReportRepository bookReportRepository;
	private final TeacherClassRepository teacherClassRepository;
	private final StudentClassRepository studentClassRepository;
	private final BookService bookService;
	private final ClassService classService;
	private final BookReportTeacherService bookReportTeacherService;
	private final BookReportStudentService bookReportStudentService;
	private final BookRecommendService bookRecommendService;
	private final BookStatusMapper bookStatusMapper;
	private final BookRecommendRepository bookRecommendRepository;

	public void addHomework(BookRequest request, User user) {
		if (user.getUserType() == UserType.학생) {
			throw new ForbiddenException("숙제도서를 생성할 권한이 없습니다.");
		}
		Book book = bookService.findBookById(request.bookId());
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성)
			.map(TeacherClass::getClassRoom)
			.orElseThrow(() -> new NoSuchElementException("활성 상태의 교사 학급 정보가 존재하지 않습니다."));
		if (homeworkRepository.existsByBookAndUserAndClassRoom(book, user, classRoom)) {
			throw new IllegalArgumentException("이미 숙제 도서로 등록했습니다.");
		}
		Homework homework = Homework.builder().book(book).user(user).classRoom(classRoom).build();
		homeworkRepository.save(homework);
		homeworkRepository.flush();
		// 추천 도서에도 추가
		if (!bookRecommendRepository.existsByBookAndUserAndClassRoom(book, user, classRoom)) {
			bookRecommendService.addBookRecommend(request, user);
		}
	}

	public void deleteHomework(BookRequest request, User user) {
		if (user.getUserType() == UserType.학생) {
			throw new ForbiddenException("숙제도서를 삭제할 권한이 없습니다.");
		}
		Book book = bookService.findBookById(request.bookId());
		ClassRoom classRoom = teacherClassRepository.findByUserIdAndActive(user.getId(), Active.활성)
			.map(TeacherClass::getClassRoom)
			.orElseThrow(() -> new NoSuchElementException("활성 상태의 교사 학급 정보가 존재하지 않습니다."));
		Homework homework = homeworkRepository.findByBookAndUserAndClassRoom(book, user, classRoom)
			.orElseThrow(() -> new IllegalArgumentException("숙제 도서로 등록한 도서가 아닙니다."));
		homeworkRepository.delete(homework);
	}

	public HomeworkTeacherResponse getHomeworkTeacherClass(User user, Pageable pageable) {
		ClassRoom userClassRoom = classService.getTecherClassRoom(user);
		int studentCount = studentClassRepository.countByClassRoomAndAuthStatus(userClassRoom, AuthStatus.승인);

		Page<Homework> homeworks = homeworkRepository.findByClassRoom(userClassRoom, pageable);

		List<HomeworkTeacherDetail> details = homeworks.stream()
			.map(homework -> getHomeworkDetailTeacher(homework.getId(), user))
			.toList();
		Page<HomeworkTeacherDetail> content = new PageImpl<>(details, pageable, homeworks.getTotalElements());
		return new HomeworkTeacherResponse(studentCount, content);
	}

	public HomeworkTeacherDetail getHomeworkDetailTeacher(Long homeworkId, User user) {
		Homework homework = homeworkRepository.findById(homeworkId)
			.orElseThrow(() -> new NoSuchElementException("해당하는 숙제가 없습니다."));
		if (!homework.getUser().equals(user)) {
			throw new IllegalArgumentException("숙제를 생성한 교사가 아닙니다.");
		}

		int submitCount = bookReportRepository.countByHomeworkId(homeworkId);
		List<BookReportTeacher> bookReports = bookReportTeacherService.getReportTeacherHomeworkResponses(
			homeworkId);
		BookStatus bookStatus = bookStatusMapper.mapBookStatus(homework.getBook(), user);
		return HomeworkTeacherDetail.of(homework, submitCount, bookReports, bookStatus);
	}

	public HomeworkTeacherDetailPage getHomeworkDetailTeacherPage(Long homeworkId, User user, String approveStatus,
		Pageable pageable) {
		Homework homework = homeworkRepository.findById(homeworkId)
			.orElseThrow(() -> new NoSuchElementException("해당하는 숙제가 없습니다."));
		if (!homework.getUser().equals(user)) {
			throw new IllegalArgumentException("숙제를 생성한 교사가 아닙니다.");
		}

		int submitCount = bookReportRepository.countByHomeworkId(homeworkId);
		Page<BookReportTeacher> bookReports = bookReportTeacherService.getReportTeacherHomeworkPageResponses(
			homeworkId, approveStatus, pageable);
		BookStatus bookStatus = bookStatusMapper.mapBookStatus(homework.getBook(), user);
		return HomeworkTeacherDetailPage.of(homework, submitCount, bookReports, bookStatus);
	}

	public HomeworkStudentResponse getHomeworkStudentClass(User user, Pageable pageable) {
		ClassRoom userClassRoom = classService.getUserClassRoom(user);

		Page<Homework> homeworks = homeworkRepository.findByClassRoom(userClassRoom, pageable);

		List<HomeworkStudentDetail> details = homeworks.stream()
			.map(homework -> getHomeworkDetailStudent(homework.getId(), user))
			.toList();
		Page<HomeworkStudentDetail> content = new PageImpl<>(details, pageable, homeworks.getTotalElements());
		return new HomeworkStudentResponse(content);
	}

	public HomeworkStudentDetail getHomeworkDetailStudent(Long homeworkId, User user) {
		Homework homework = homeworkRepository.findById(homeworkId)
			.orElseThrow(() -> new NoSuchElementException("해당하는 숙제가 없습니다."));
		ClassRoom classRoom = homework.getClassRoom();
		StudentClass studentClass = studentClassRepository.findByUserAndClassRoom(user, classRoom);
		if (studentClass == null) {
			throw new IllegalArgumentException("해당 숙제에 해당하는 학급의 학생이 아닙니다.");
		}

		Book book = homework.getBook();
		boolean submitStatus = bookReportRepository.countByUserIdAndHomeworkId(user.getId(), homeworkId) > 0;
		List<BookReportStudent> bookReports = bookReportStudentService.getReportStudentHomeworkResponses(
			book.getId(), user.getId());
		BookStatus bookStatus = bookStatusMapper.mapBookStatus(homework.getBook(), user);
		return HomeworkStudentDetail.of(homework, submitStatus, bookReports, bookStatus);
	}

	public HomeworkStudentDetailPage getHomeworkDetailStudentPage(Long homeworkId, User user, Pageable pageable) {
		Homework homework = homeworkRepository.findById(homeworkId)
			.orElseThrow(() -> new NoSuchElementException("해당하는 숙제가 없습니다."));
		ClassRoom classRoom = homework.getClassRoom();
		StudentClass studentClass = studentClassRepository.findByUserAndClassRoom(user, classRoom);
		if (studentClass == null) {
			throw new IllegalArgumentException("해당 숙제에 해당하는 학급의 학생이 아닙니다.");
		}

		Book book = homework.getBook();
		boolean submitStatus = bookReportRepository.countByUserIdAndHomeworkId(user.getId(), homeworkId) > 0;
		Page<BookReportStudent> bookReports = bookReportStudentService.getReportStudentHomeworkPageResponses(
			book.getId(), user.getId(), pageable);
		BookStatus bookStatus = bookStatusMapper.mapBookStatus(homework.getBook(), user);
		return HomeworkStudentDetailPage.of(homework, submitStatus, bookReports, bookStatus);
	}
}
