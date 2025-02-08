package com.ssafy.ourdoc.domain.bookreport.repository;

import static com.ssafy.ourdoc.global.common.enums.UserType.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.data.entity.BookReportSample;
import com.ssafy.ourdoc.data.entity.BookSample;
import com.ssafy.ourdoc.data.entity.ClassRoomSample;
import com.ssafy.ourdoc.data.entity.SchoolSample;
import com.ssafy.ourdoc.data.entity.StudentClassSample;
import com.ssafy.ourdoc.data.entity.TeacherClassSample;
import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class BookReportQueryRepositoryImplTest {

	@Autowired
	private BookReportRepository bookReportRepository;

	@Autowired
	private EntityManager em;

	private School school;
	private ClassRoom classRoom;
	private User teacher;
	private User student;
	private StudentClass studentClass;
	private TeacherClass teacherClass;
	private Book book;
	private BookReport bookReport;

	@BeforeEach
	void setUp() {
		school = SchoolSample.school();
		em.persist(school);

		classRoom = ClassRoomSample.classRoom(school);
		em.persist(classRoom);

		student = UserSample.user(학생);
		teacher = UserSample.user(교사);
		em.persist(student);
		em.persist(teacher);

		studentClass = StudentClassSample.studentClass(student, classRoom);
		teacherClass = TeacherClassSample.teacherClass(teacher, classRoom);
		em.persist(studentClass);
		em.persist(teacherClass);

		book = BookSample.book("테스트제목");
		em.persist(book);

		bookReport = BookReportSample.bookReport(studentClass, book);
		em.persist(bookReport);

		em.flush();
		em.clear();
	}
}
