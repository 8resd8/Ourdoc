package com.ssafy.ourdoc.domain.classroom.service;

import java.time.Year;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.classroom.dto.CreateClassRequest;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.entity.TeacherClass;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherClassRepository;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.global.common.enums.Active;

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

	public void createClass(Long teacherId, CreateClassRequest request) {
		Teacher findTeacher = getFindTeacher(teacherId);
		School findSchool = getFindSchool(request);

		isExistClass(request);

		ClassRoom classRoom = creatClassRoom(request, findSchool);
		createTeacherClass(findTeacher, classRoom);
	}

	private TeacherClass createTeacherClass(Teacher findTeacher, ClassRoom classRoom) {
		TeacherClass teacherClass = TeacherClass.builder()
			.user(findTeacher.getUser())
			.classRoom(classRoom)
			.active(Active.활성)  // 기본값 설정
			.build();
		return teacherClassRepository.save(teacherClass);
	}

	private ClassRoom creatClassRoom(CreateClassRequest request, School findSchool) {
		ClassRoom classRoom = ClassRoom.builder()
			.school(findSchool)
			.classNumber(request.classNumber())
			.grade(request.grade())
			.year(Year.of(request.year()))
			.build();
		classRoomRepository.save(classRoom);
		return classRoom;
	}

	private void isExistClass(CreateClassRequest request) {
		if (classRoomRepository.findByGradeAndClassNumberAndYear(request.grade(), request.classNumber(), Year.now())
			.isPresent()) {
			throw new IllegalArgumentException("이미 등록된 학급이 있습니다.");
		}
	}

	private School getFindSchool(CreateClassRequest request) {
		School findSchool = schoolRepository.findBySchoolName(request.schoolName())
			.orElseThrow(() -> new IllegalArgumentException("학교가 없습니다."));
		return findSchool;
	}

	private Teacher getFindTeacher(Long teacherId) {
		Teacher findTeacher = teacherRepository.findById(teacherId)
			.orElseThrow(() -> new IllegalArgumentException("해당 Teacher ID를 찾을 수 없습니다: " + teacherId));
		return findTeacher;
	}
}
