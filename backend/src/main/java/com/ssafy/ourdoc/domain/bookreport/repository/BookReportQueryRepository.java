package com.ssafy.ourdoc.domain.bookreport.repository;

import java.util.List;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReportDetailDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherDto;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherDtoWithId;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherRequest;

public interface BookReportQueryRepository {

	List<ReportTeacherDto> bookReports(Long userId, ReportTeacherRequest request);

	BookReportDetailDto bookReportDetail(Long reportId);

	List<ReportTeacherDtoWithId> bookReportsHomework(Long homeworkId);
}
