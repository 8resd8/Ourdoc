package com.ssafy.ourdoc.classroom.service;

import java.time.Year;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.classroom.dto.CreateClassRequest;
import com.ssafy.ourdoc.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.classroom.repository.ClassRoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassService {

	private final ClassRoomRepository classRoomRepository;

	public void createClass(Long teacherId, CreateClassRequest request) {
		// 학급 생성
		ClassRoom classRoom = ClassRoom.builder()
			.classNumber(request.classNumber())
			.grade(request.grade())
			.year(Year.of(request.year()))
			.build();
		classRoomRepository.save(classRoom);


	}
}
