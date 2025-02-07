package com.ssafy.ourdoc.data.entity;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.bookreport.entity.BookReport;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.global.common.enums.OcrCheck;

public class BookReportSample {
	private BookReportSample() {
	}

	// Homework는 선택값이다.
	public static BookReport bookReport(StudentClass studentClass, Book book) {
		return BookReport.builder()
			.studentClass(studentClass)
			.book(book)
			.beforeContent("테스트이전")
			.afterContent("테스트이후")
			.ocrCheck(OcrCheck.사용)
			.imagePath("테스트이미지")
			.build();
	}

	public static BookReport bookReport(StudentClass studentClass, Book book, Homework homework) {
		return BookReport.builder()
			.studentClass(studentClass)
			.book(book)
			.homework(homework)
			.beforeContent("테스트이전")
			.afterContent("테스트이후")
			.ocrCheck(OcrCheck.사용)
			.imagePath("테스트이미지")
			.build();
	}
}
