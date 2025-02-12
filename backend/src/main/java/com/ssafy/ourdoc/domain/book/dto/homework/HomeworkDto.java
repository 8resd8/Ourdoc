package com.ssafy.ourdoc.domain.book.dto.homework;

import java.time.LocalDateTime;

import com.ssafy.ourdoc.domain.book.dto.BookResponse;

public record HomeworkDto(
	BookResponse book,
	LocalDateTime createdAt) {
}
