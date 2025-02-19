package com.ssafy.ourdoc.domain.classroom.entity;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "school")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class School {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "school_id", nullable = false)
	private Long id;

	@Column(name = "school_name", nullable = false)
	private String schoolName;

	@Column(name = "address")
	private String address;

	@OneToMany(mappedBy = "school")
	private List<ClassRoom> classRoomList = new ArrayList<>();

	@Builder
	public School(String schoolName, String address) {
		this.schoolName = schoolName;
		this.address = address;
	}
}
