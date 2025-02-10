package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.Year;
import java.util.List;

public record HomeworkResponseTeacher(
	String schoolName,
	int grade,
	int classNumber,
	Year year,
	int studentCount,
	List<HomeworkDetailTeacher> homework
) {
}
