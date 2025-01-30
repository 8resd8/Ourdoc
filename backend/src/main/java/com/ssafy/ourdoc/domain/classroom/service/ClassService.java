package com.ssafy.ourdoc.domain.classroom.service;

import java.time.Year;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.classroom.dto.CreateClassRequest;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.global.common.enums.Active;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassService {

	private final ClassRoomRepository classRoomRepository;
	private final TeacherClassRepository teacherClassRepository;
	// private final TeacherRepository teacherRepository;

	public void createClass(Long teacherId, CreateClassRequest request) {
		// 학급 생성
		ClassRoom classRoom = ClassRoom.builder()
			.classNumber(request.classNumber())
			.grade(request.grade())
			.year(Year.of(request.year()))
			.build();
		classRoomRepository.save(classRoom);

		// Teacher teacher = teacherRepository.findById(teacherId)
		// 	.orElseThrow(() -> new IllegalArgumentException("해당 Teacher ID를 찾을 수 없습니다: " + teacherId));

		// teacher_class 테이블에 추가
		// TeacherClass teacherClass = TeacherClass.builder()
		// 	.teacher(teacher)
		// 	.classRoom(classRoom)
		// 	.active(Active.활성)  // 기본값 설정
		// 	.build();
		// teacherClassRepository.save(teacherClass);
	}
}
