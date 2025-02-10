package com.ssafy.ourdoc.domain.bookreport.entity;

import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;
import com.ssafy.ourdoc.global.common.enums.EvaluatorType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book_report_feedback")
public class BookReportFeedBack extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_report_feedback_id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "book_report_id")
	private BookReport bookReport;

	@Column(name = "evaluator_type")
	@Enumerated(EnumType.STRING)
	private EvaluatorType evaluatorType;

	@Column(name = "comment")
	private String comment;

	@Builder
	public BookReportFeedBack(BookReport bookReport, EvaluatorType type, String comment) {
		this.bookReport = bookReport;
		this.evaluatorType = type;
		this.comment = comment;
	}

	public void updateComment(String comment) {
		if (bookReport.getApproveTime() != null) {
			throw new IllegalArgumentException("칭찬도장이 있으면 더이상 수정 및 삭제할 수 없습니다.");
		}
		this.evaluatorType = EvaluatorType.교사;
		this.comment = comment;
	}
}
