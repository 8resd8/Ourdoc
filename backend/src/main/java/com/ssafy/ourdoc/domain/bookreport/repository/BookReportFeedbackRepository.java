package com.ssafy.ourdoc.domain.bookreport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.bookreport.entity.BookReportFeedBack;
import com.ssafy.ourdoc.global.common.enums.EvaluatorType;

public interface BookReportFeedbackRepository extends JpaRepository<BookReportFeedBack, Long> {
	Optional<BookReportFeedBack> findByBookReportId(Long bookReportId);

	List<BookReportFeedBack> findAllByBookReportId(Long bookReportId);

	Optional<BookReportFeedBack> findByBookReportIdAndEvaluatorType(Long bookReportId, EvaluatorType teacher);
}
