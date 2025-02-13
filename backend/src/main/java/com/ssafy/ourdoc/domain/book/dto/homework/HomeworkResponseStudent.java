package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.Year;
import java.util.List;

public record HomeworkResponseStudent(
	String schoolName,
	int grade,
	int classNumber,
	Year year,
	List<HomeworkDetailStudent> homework
) {
}
