package com.ssafy.ourdoc.domain.classroom.service;

import java.time.Year;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.classroom.dto.CreateClassRequest;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.repository.ClassRoomRepository;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
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

	public void createClass(User user, CreateClassRequest request) {
		Teacher findTeacher = getFindTeacher(user.getId());
		School findSchool = schoolRepository.findBySchoolNameAndAddress(request.schoolName(), request.schoolAddress());

		validateDuplicateClass(request);

		ClassRoom classRoom = creatClassRoom(request, findSchool);
		createTeacherClass(findTeacher, classRoom);
	}

	private TeacherClass createTeacherClass(Teacher findTeacher, ClassRoom classRoom) {
		TeacherClass teacherClass = TeacherClass.builder()
			.user(findTeacher.getUser())
			.classRoom(classRoom)
			.active(Active.활성)
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
		return classRoomRepository.save(classRoom);
	}

	private void validateDuplicateClass(CreateClassRequest request) {
		if (classRoomRepository.findByGradeAndClassNumberAndYear(request.grade(), request.classNumber(), Year.now())
			.isPresent()) {
			throw new IllegalArgumentException("이미 등록된 학급이 있습니다.");
		}
	}

	private Teacher getFindTeacher(Long userId) {
		return teacherRepository.findTeacherByUserId(userId)
			.orElseThrow(() -> new NoSuchElementException("해당 User ID를 찾을 수 없습니다"));
	}
}
