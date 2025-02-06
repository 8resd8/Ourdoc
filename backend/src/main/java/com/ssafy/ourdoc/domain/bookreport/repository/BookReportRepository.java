package com.ssafy.ourdoc.domain.bookreport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;

public interface BookReportRepository extends JpaRepository<BookReport, Long>, BookReportQueryRepository {
	List<BookReport> findByStudentClassId(Long studentClassId);
}
