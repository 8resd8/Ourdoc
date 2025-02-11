package com.ssafy.ourdoc.domain.bookreport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDetailDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherDtoWithId;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherRequest;

public interface BookReportQueryRepository {

	Page<ReportTeacherDto> bookReports(Long userId, ReportTeacherRequest request, Pageable pageable);

	BookReportDetailDto bookReportDetail(Long reportId);

	List<ReportTeacherDtoWithId> bookReportsHomework(Long homeworkId);

	List<BookReportHomeworkStudent> bookReportsHomeworkStudents(Long homeworkId);

	long myBookReportsCount(Long userId, int grade);

	double classAverageBookReportsCount(Long userId, int grade);

	long classHighestBookReportCount(Long userId, int grade);
}
