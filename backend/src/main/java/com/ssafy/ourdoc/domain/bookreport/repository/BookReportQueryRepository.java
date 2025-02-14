package com.ssafy.ourdoc.domain.bookreport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDailyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDetailDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudentDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportMonthlyStatisticsDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportRankDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherDtoWithId;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherRequest;

public interface BookReportQueryRepository {

	Page<ReportTeacherDto> bookReports(Long userId, ReportTeacherRequest request, Pageable pageable);

	BookReportDetailDto bookReportDetail(Long reportId);

	List<ReportTeacherDtoWithId> bookReportsHomework(Long homeworkId);

	Page<ReportTeacherDtoWithId> bookReportsHomeworkPage(Long homeworkId, Pageable pageable);
	
	List<BookReportHomeworkStudentDto> bookReportsHomeworkStudents(Long homeworkId, Long userId);

	long myBookReportsCount(Long userId, int grade);

	double classAverageBookReportsCount(Long userId, int grade);

	long classHighestBookReportCount(Long userId, int grade);

	List<BookReportMonthlyStatisticsDto> myMonthlyBookReportCount(Long userId, int grade);

	List<BookReportMonthlyStatisticsDto> classMonthlyBookReportCount(Long userId);

	List<BookReportDailyStatisticsDto> myDailyBookReportCount(Long userId, int grade, int month);

	List<BookReportDailyStatisticsDto> classDailyBookReportCount(Long userId, int month);

	List<BookReportRankDto> bookReportRank(Long userId);
}
