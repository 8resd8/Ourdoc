package com.ssafy.ourdoc.domain.book.dto;

import java.time.Year;

import org.springframework.data.domain.Page;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.bookreport.dto.teacher.BookReportTeacher;

import lombok.Builder;

@Builder
public record BookTeacherDetailResponse(
	Long bookId,
	String title,
	String author,
	String genre,
	String description,
	String publisher,
	Year publishYear,
	String imageUrl,
	BookStatus bookStatus,
	int homeworkSubmitCount,
	Page<BookReportTeacher> bookReports
) {
	public static BookTeacherDetailResponse of(Book book, BookStatus bookStatus,
		int submitCount, Page<BookReportTeacher> bookReports) {
		return BookTeacherDetailResponse.builder()
			.bookId(book.getId())
			.title(book.getTitle())
			.author(book.getAuthor())
			.genre(book.getGenre())
			.publisher(book.getPublisher())
			.publishYear(book.getPublishYear())
			.imageUrl(book.getImageUrl())
			.description(book.getDescription())
			.bookStatus(bookStatus)
			.homeworkSubmitCount(submitCount)
			.bookReports(bookReports)
			.build();
	}
}
