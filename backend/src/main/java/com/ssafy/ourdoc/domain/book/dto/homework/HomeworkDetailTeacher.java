package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.ReportTeacherResponseWithId;
import com.ssafy.ourdoc.domain.classroom.dto.SchoolClassDto;

import lombok.Builder;

@Builder
public record HomeworkDetailTeacher(
	Long id,
	BookResponse book,
	LocalDateTime createdAt,
	int submitCount,
	List<SchoolClassDto> schoolClass,
	List<ReportTeacherResponseWithId> bookreports
) {
}
