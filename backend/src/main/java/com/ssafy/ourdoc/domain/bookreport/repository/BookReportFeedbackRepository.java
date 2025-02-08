package com.ssafy.ourdoc.domain.bookreport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.bookreport.entity.BookReportFeedBack;

public interface BookReportFeedbackRepository extends JpaRepository<BookReportFeedBack, Long> {
	Optional<BookReportFeedBack> findByBookReportId(Long bookReportId);
}
