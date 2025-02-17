package com.ssafy.ourdoc.domain.book.dto;

import java.time.Year;

import org.springframework.data.domain.Page;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportStudent;

import lombok.Builder;

@Builder
public record BookStudentDetailResponse(
	Long bookId,
	String title,
	String author,
	String genre,
	String description,
	String publisher,
	Year publishYear,
	String imageUrl,
	BookStatus bookStatus,
	boolean homeworkSubmitStatus,
	Page<BookReportStudent> bookReports
) {
	public static BookStudentDetailResponse of(Book book, BookStatus bookStatus,
		boolean submitStatus, Page<BookReportStudent> bookReports) {
		return BookStudentDetailResponse.builder()
			.bookId(book.getId())
			.title(book.getTitle())
			.author(book.getAuthor())
			.genre(book.getGenre())
			.publisher(book.getPublisher())
			.publishYear(book.getPublishYear())
			.imageUrl(book.getImageUrl())
			.description(book.getDescription())
			.bookStatus(bookStatus)
			.homeworkSubmitStatus(submitStatus)
			.bookReports(bookReports)
			.build();
	}
}
