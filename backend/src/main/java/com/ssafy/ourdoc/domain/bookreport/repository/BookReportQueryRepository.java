package com.ssafy.ourdoc.domain.bookreport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDetailDto;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStudentDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.BookReportTeacherDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherRequest;

public interface BookReportQueryRepository {

	Page<ReportTeacherDto> bookReports(Long userId, ReportTeacherRequest request, Pageable pageable);

	BookReportDetailDto bookReportDetail(Long reportId);

	List<BookReportTeacherDto> bookReportsHomework(Long homeworkId);

	Page<BookReportTeacherDto> bookReportsHomeworkPage(Long homeworkId, String approveStatus, Pageable pageable);

	Page<BookReportTeacherDto> bookReportsTeacherPage(Long bookId, Pageable pageable);

	List<BookReportStudentDto> bookReportsHomeworkStudents(Long bookId, Long userId);

	Page<BookReportStudentDto> bookReportsHomeworkStudentsPage(Long bookId, Long userId, Pageable pageable);

}
