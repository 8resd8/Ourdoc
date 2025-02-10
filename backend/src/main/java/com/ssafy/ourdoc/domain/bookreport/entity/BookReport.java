package com.ssafy.ourdoc.domain.bookreport.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.Homework;
import com.ssafy.ourdoc.domain.user.student.entity.StudentClass;
import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.common.enums.OcrCheck;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookReport extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_report_id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_class_id")
	private StudentClass studentClass;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "homework_id")
	private Homework homework;

	@Column(name = "before_content", columnDefinition = "TEXT")
	private String beforeContent;

	@Column(name = "after_content", columnDefinition = "TEXT")
	private String afterContent;

	@Column(name = "ocr_check", nullable = false)
	@Enumerated(EnumType.STRING)
	private OcrCheck ocrCheck;

	@Column(name = "image_path")
	private String imagePath;

	@Column(name = "approve_time")
	private LocalDateTime approveTime;

	@OneToMany(mappedBy = "bookReport", fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.REMOVE})
	private List<BookReportFeedBack> bookReportFeedBack = new ArrayList<>();


	@Builder
	public BookReport(StudentClass studentClass, Book book, Homework homework, String beforeContent,
		String afterContent, OcrCheck ocrCheck, String imagePath) {
		this.studentClass = studentClass;
		this.book = book;
		this.homework = homework;
		this.beforeContent = beforeContent;
		this.afterContent = afterContent;
		this.ocrCheck = ocrCheck;
		this.imagePath = imagePath;
	}

	public void approveStamp() {
		this.approveTime = LocalDateTime.now();
	}

	public void saveAfterContent(String afterContent) {
		this.afterContent = afterContent;
	}
}

