package com.ssafy.ourdoc.domain.bookreport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.bookreport.entity.BookReportFeedBack;

public interface BookReportFeedbackRepository extends JpaRepository<BookReportFeedBack, Long> {
}
