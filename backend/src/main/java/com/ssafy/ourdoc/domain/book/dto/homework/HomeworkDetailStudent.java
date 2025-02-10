package com.ssafy.ourdoc.domain.book.dto.homework;

import java.util.List;

import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;

import lombok.Builder;

@Builder
public record HomeworkDetailStudent(
	HomeworkDto homework,
	List<BookReportHomeworkStudent> bookreports
) {
}
