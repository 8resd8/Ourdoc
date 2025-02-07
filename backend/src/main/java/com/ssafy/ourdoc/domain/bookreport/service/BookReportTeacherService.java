package com.ssafy.ourdoc.domain.bookreport.service;

import static com.ssafy.ourdoc.global.common.enums.ApproveStatus.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherListResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherRequest;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherResponse;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookReportTeacherService {

	private final BookReportRepository bookReportRepository;

	public ReportTeacherListResponse getBookReports(User user, ReportTeacherRequest request) {
		List<ReportTeacherResponse> convertDto = getReportTeacherResponses(
			user, request);

		return new ReportTeacherListResponse(convertDto);
	}

	private List<ReportTeacherResponse> getReportTeacherResponses(User user, ReportTeacherRequest request) {
		List<ReportTeacherResponse> convertDto = bookReportRepository.bookReports(user.getId(), request).stream()
			.map(dto -> new ReportTeacherResponse(
				dto.bookTitle(),
				dto.studentNumber(),
				dto.studentName(),
				dto.createdAt(),
				(dto.approveTime() == null) ? 있음 : 없음
			))
			.toList();
		return convertDto;
	}
}
