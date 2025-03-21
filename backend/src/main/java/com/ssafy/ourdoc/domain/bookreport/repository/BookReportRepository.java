package com.ssafy.ourdoc.domain.bookreport.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;

public interface BookReportRepository extends JpaRepository<BookReport, Long>, BookReportQueryRepository {

	@Query("select br from BookReport br join br.studentClass sc join sc.classRoom c where sc.user.id = :userId and c.grade = :grade")
	Page<BookReport> findByUserIdAndGrade(@Param("userId") Long userId, @Param("grade") int grade, Pageable pageable);

	@Query("select count(*) from BookReport br join br.studentClass sc where sc.user.id = :userId and br.book.id = :bookId")
	int countByUserIdAndBookId(@Param("userId") Long userId, @Param("bookId") Long bookId);

	@Query("select b from BookReport b where b.studentClass.user.id = :userId and b.id = :bookReportId")
	Optional<BookReport> findByBookReport(@Param("bookReportId") Long bookReportId, @Param("userId") Long userId);

	@Query("select count(*) from BookReport br where br.homework.id = :homeworkId")
	int countByHomeworkId(Long homeworkId);

	@Query("select count(*) from BookReport br join br.studentClass sc where sc.user.id = :userId and br.homework.id = :homeworkId")
	int countByUserIdAndHomeworkId(@Param("userId") Long userId, @Param("homeworkId") Long homeworkId);
}