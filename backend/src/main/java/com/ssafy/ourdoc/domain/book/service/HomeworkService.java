package com.ssafy.ourdoc.domain.book.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkDetailStudent;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkDetailTeacher;
import com.ssafy.ourdoc.domain.book.dto.homework.HomeworkDto;
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
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.student.repository.StudentClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.service.TeacherService;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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

	public List<HomeworkResponseTeacher> getHomeworkTeachers(BookSearchRequest request, User user) {
		List<SchoolClassDto> schoolClasses = teacherService.getClassRoomsTeacher(user.getId());
		List<HomeworkResponseTeacher> responses = schoolClasses.stream()
			.map(schoolClass -> {
				List<Homework> homeworks = homeworkRepository.findByClassIdAndSearchBook(schoolClass.id(),
					request.title(), request.author(), request.publisher());
				List<HomeworkDetailTeacher> homeworkDetails = homeworks.stream()
					.map(homework -> getHomeworkDetailTeacher(homework.getId(), user))
					.toList();

				return new HomeworkResponseTeacher(
					schoolClass.schoolName(),
					schoolClass.grade(),
					schoolClass.classNumber(),
					schoolClass.year(),
					schoolClass.studentCount(),
					homeworkDetails
				);
			})
			.toList();

		return responses;
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

	public HomeworkDetailStudent getHomeworkDetailStudent(Long homeworkId, User user) {
		Homework homework = homeworkRepository.findById(homeworkId)
			.orElseThrow(() -> new NoSuchElementException("해당하는 숙제가 없습니다."));
		ClassRoom classRoom = homework.getClassRoom();
		StudentClass studentClass = studentClassRepository.findByUserAndClassRoom(user, classRoom);
		if (studentClass == null) {
			throw new IllegalArgumentException("해당 숙제에 해당하는 학급의 학생이 아닙니다.");
		}

		HomeworkDto homeworkDto = new HomeworkDto(BookResponse.of(homework.getBook()), homework.getCreatedAt());
		List<BookReportHomeworkStudent> bookReports = bookReportRepository.bookReportsHomeworkStudents(homeworkId);

		return HomeworkDetailStudent.builder()
			.homework(homeworkDto)
			.bookreports(bookReports)
			.build();
	}
}
