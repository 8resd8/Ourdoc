package com.ssafy.ourdoc.domain.bookreport.service;

import static com.ssafy.ourdoc.global.common.enums.ApproveStatus.*;
import static com.ssafy.ourdoc.global.common.enums.EvaluatorType.*;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDetailDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDetailResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.FeedbackRequest;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReportFeedBack;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportFeedbackRepository;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.global.common.enums.ApproveStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookReportService {

	private final BookReportRepository bookReportRepository;
	private final BookReportFeedbackRepository feedbackRepository;

	public BookReportDetailResponse getBookReportDetail(Long bookReportId) {
		BookReportDetailDto detailDto = bookReportRepository.bookReportDetail(bookReportId);
		if (detailDto == null) {
			throw new NoSuchElementException("해당 독서록을 찾을 수 없습니다.");
		}

		ApproveStatus approveStatus = (detailDto.approveTime() != null) ? 있음 : 없음;
		String aiComment = detailDto.aiComment() == null ? "없음" : detailDto.aiComment();
		String teacherComment = detailDto.teacherComment() == null ? "없음" : detailDto.teacherComment();
		
		return BookReportDetailResponse.builder()
			.bookTitle(detailDto.bookTitle())
			.author(detailDto.author())
			.publisher(detailDto.publisher())
			.createdAt(detailDto.createdAt())
			.content(detailDto.content())
			.aiComment(aiComment)
			.teacherComment(teacherComment)
			.approveStatus(approveStatus)
			.build();
	}

	// 작성한 독서록 기반 AI 피드백 받은것 처리
	public void saveBookReportFeedback(FeedbackRequest request) {
		BookReport bookReport = bookReportRepository.findById(request.bookReportId()).orElseThrow();
		bookReport.saveAfterContent(request.afterContent());

		BookReportFeedBack bookReportFeedBack = BookReportFeedBack.builder().bookReport(bookReport).type(인공지능).build();

		feedbackRepository.save(bookReportFeedBack);
	}

}
