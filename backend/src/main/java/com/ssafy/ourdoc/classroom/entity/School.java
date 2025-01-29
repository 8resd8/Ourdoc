package com.ssafy.ourdoc.classroom.entity;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.ourdoc.global.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class School extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "school_id", nullable = false)
	private Long id;

	@Column(name = "school_name", nullable = false)
	private String schoolName;

	@Column(name = "address")
	private String address;

	@OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
	private List<ClassRoom> classRoomList = new ArrayList<>();
}
