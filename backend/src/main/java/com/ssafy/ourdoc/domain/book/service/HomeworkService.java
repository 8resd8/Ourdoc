package com.ssafy.ourdoc.domain.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkDetailStudent;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkDetailTeacher;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkDto;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkPageable;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkResponseStudent;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkResponseTeacher;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.book.repository.HomeworkRepository;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherResponseWithId;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.bookreport.service.BookReportTeacherService;
import com.ssafy.ourdoc.domain.classroom.dto.SchoolClassDto;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.classroom.service.ClassService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.student.service.StudentService;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.service.TeacherService;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.ApproveStatus;
import com.ssafy.ourdoc.global.common.enums.SubmitStatus;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeworkService {

	private final HomeworkRepository homeworkRepository;
	private final TeacherClassRepository teacherClassRepository;
	private final BookReportTeacherService bookReportTeacherService;
	private final TeacherService teacherService;
	private final BookService bookService;
	private final BookRepository bookRepository;
	private final BookReportRepository bookReportRepository;
	private final ClassRoomRepository classRoomRepository;
	private final StudentClassRepository studentClassRepository;
	private final StudentService studentService;
	private final ClassService classService;

	public void addHomework(BookRequest request, User user) {
		if (user.getUserType().equals(UserType.학생)) {
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
	}

	public void deleteHomework(BookRequest request, User user) {
		if (user.getUserType().equals(UserType.학생)) {
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

	public HomeworkResponseTeacher getHomeworkTeachers(BookSearchRequest request, User user,
		HomeworkPageable pageable) {
		Pageable bookPageable = PageRequest.of(pageable.bookPage(), pageable.bookSize());
		Pageable reportPageable = PageRequest.of(pageable.reportPage(), pageable.reportSize());

		List<SchoolClassDto> schoolClasses = teacherService.getClassRoomsTeacher(user.getId());
		List<Homework> homeworks = new ArrayList<>();
		schoolClasses.forEach(
			schoolClassDto -> homeworks.addAll(homeworkRepository.findByClassRoomId(schoolClassDto.id())));

		int start = (int)bookPageable.getOffset();
		int end = Math.min(start + bookPageable.getPageSize(), homeworks.size());

		List<Homework> pagedHomeworks = homeworks.subList(start, end);

		List<HomeworkDetailTeacher> homeworkDetailTeachers = pagedHomeworks.stream()
			.map(homework -> getHomeworkDetailTeacher(homework.getId(), user))
			.toList();
		Page<HomeworkDetailTeacher> content = new PageImpl<>(homeworkDetailTeachers, bookPageable,
			homeworks.size());
		return new HomeworkResponseTeacher(content);
	}

	public HomeworkDetailTeacher getHomeworkDetailTeacher(Long homeworkId, User user) {
		Homework homework = homeworkRepository.findById(homeworkId)
			.orElseThrow(() -> new NoSuchElementException("해당하는 숙제가 없습니다."));
		if (!homework.getUser().equals(user)) {
			throw new IllegalArgumentException("숙제를 생성한 교사가 아닙니다.");
		}
		List<ReportTeacherResponseWithId> bookreports = bookReportTeacherService.getReportTeacherHomeworkResponses(
			homeworkId);

		return HomeworkDetailTeacher.builder()
			.id(homeworkId)
			.book(BookResponse.of(homework.getBook()))
			.createdAt(homework.getCreatedAt())
			.submitCount(bookreports.size())
			.bookreports(bookreports)
			.build();
	}

	// public List<HomeworkResponseStudent> getHomeworkStudents(BookSearchRequest request, User user) {
	// 	List<SchoolClassDto> schoolClasses = studentService.getClassRoomsStudent(user.getId());
	// 	List<HomeworkResponseStudent> responses = schoolClasses.stream()
	// 		.map(schoolClass -> getHomeworkResponseStudent(request, user, schoolClass))
	// 		.toList();
	//
	// 	return responses;
	// }

	private HomeworkResponseStudent getHomeworkResponseStudent(BookSearchRequest request, User user,
		SchoolClassDto schoolClassDto, Pageable pageable) {
		List<Homework> homeworks = homeworkRepository.findByClassIdAndSearchBook(schoolClassDto.id(),
			request.title(), request.author(), request.publisher());
		List<HomeworkDetailStudent> homeworkDetails = homeworks.stream()
			.map(homework -> getHomeworkDetailStudent(homework.getId(), user))
			.toList();

		return new HomeworkResponseStudent(
			schoolClassDto.schoolName(),
			schoolClassDto.grade(),
			schoolClassDto.classNumber(),
			schoolClassDto.year(),
			homeworkDetails
		);
	}

	public HomeworkDetailStudent getHomeworkDetailStudent(Long homeworkId, User user) {
		Homework homework = homeworkRepository.findById(homeworkId)
			.orElseThrow(() -> new NoSuchElementException("해당하는 숙제가 없습니다."));
		ClassRoom classRoom = homework.getClassRoom();
		StudentClass studentClass = studentClassRepository.findByUserAndClassRoom(user, classRoom);
		if (studentClass == null) {
			throw new IllegalArgumentException("해당 숙제에 해당하는 학급의 학생이 아닙니다.");
		}

		HomeworkDto homeworkDto = new HomeworkDto(BookResponse.of(homework.getBook()), homework.getCreatedAt());
		List<BookReportHomeworkStudent> bookReports = bookReportRepository.bookReportsHomeworkStudents(homeworkId,
				user.getId())
			.stream()
			.map(dto -> new BookReportHomeworkStudent(
				dto.bookreportId(),
				dto.createdAt(),
				dto.homeworkId() != null ? SubmitStatus.제출 : SubmitStatus.미제출,
				dto.approveTime() != null ? ApproveStatus.있음 : ApproveStatus.없음))
			.toList();

		return HomeworkDetailStudent.builder()
			.homework(homeworkDto)
			.bookreports(bookReports)
			.build();
	}
}
