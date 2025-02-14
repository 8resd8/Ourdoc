package com.ssafy.ourdoc.domain.book.dto.homework;

import org.springframework.data.domain.Page;

public record HomeworkTeacherResponse(
	int studentCount,
	Page<HomeworkTeacherDetail> homeworks) {
}
