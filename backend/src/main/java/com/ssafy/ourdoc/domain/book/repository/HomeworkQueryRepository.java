package com.ssafy.ourdoc.domain.book.repository;

import java.time.Year;
import java.util.List;

import com.ssafy.ourdoc.domain.book.entity.Homework;

public interface HomeworkQueryRepository {
	List<Homework> findByUserIdAndYear(Long userId, Year year);

	List<Homework> findByClassIn(List<Long> classIds);

	List<Homework> findByClassIdAndSearchBook(Long classId, String title, String author, String publisher);
}
