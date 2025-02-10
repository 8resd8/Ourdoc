package com.ssafy.ourdoc.domain.bookreport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;

public interface BookReportRepository extends JpaRepository<BookReport, Long>, BookReportQueryRepository {

	@Query("select br from BookReport br join br.studentClass sc join sc.classRoom c where sc.user.id = :userId and c.grade = :grade")
	Page<BookReport> findByUserIdAndGrade(@Param("userId") Long userId, @Param("grade") int grade, Pageable pageable);
}
