package com.ssafy.ourdoc.domain.classroom.service;

import java.sql.Date;
import java.time.Year;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.classroom.dto.CreateClassRequest;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;
import com.ssafy.ourdoc.global.common.enums.UserType;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassService {

	private final ClassRoomRepository classRoomRepository;
	private final TeacherClassRepository teacherClassRepository;
	private final TeacherRepository teacherRepository;
	private final SchoolRepository schoolRepository;
	private final UserRepository userRepository;

	public void createClass(Long teacherId, CreateClassRequest request) {
		// teacherId로 user가져오기
		// Teacher findTeacher = teacherRepository.findById(teacherId).get();
		// User user = userRepository.findById(findTeacher.getUser().getId()).get();
		//
		// School findSchool = schoolRepository.findBySchoolName(request.schoolName())
		// 	.orElseThrow(() -> new IllegalArgumentException("학교가 없습니다."));

		// teacherId로 teacher 찾기
		Teacher findTeacher = teacherRepository.findById(teacherId)
			.orElseThrow(() -> new IllegalArgumentException("해당 Teacher ID를 찾을 수 없습니다: " + teacherId));

		// 학교 찾기
		School findSchool = schoolRepository.findBySchoolName(request.schoolName())
			.orElseThrow(() -> new IllegalArgumentException("학교가 없습니다."));

		// 학급 생성
		ClassRoom classRoom = ClassRoom.builder()
			.school(findSchool)
			.classNumber(request.classNumber())
			.grade(request.grade())
			.year(Year.of(request.year()))
			.build();
		classRoomRepository.save(classRoom);


		// 학급교사 테이블에도 생성
		// TeacherClass teacherClass = TeacherClass.builder()
		// 	.user(findTeacher.getUser())
		// 	.classRoom(classRoom)
		// 	.active(Active.활성)  // 기본값 설정
		// 	.build();
		// teacherClassRepository.save(teacherClass);
	}
}
