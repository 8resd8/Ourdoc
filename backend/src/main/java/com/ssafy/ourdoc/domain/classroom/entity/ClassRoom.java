package com.ssafy.ourdoc.domain.classroom.entity;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;
import com.ssafy.ourdoc.domain.user.student.entity.Student;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "class")
public class ClassRoom extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "class_id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id")
	private School school;

	@Column(name = "grade", nullable = false)
	private int grade;

	@Column(name = "class_number", nullable = false)
	private int classNumber;

	@Column(name = "year", nullable = false)
	private Year year;

	@OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Student> students = new ArrayList<>();

	@OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Teacher> teachers = new ArrayList<>();
}
