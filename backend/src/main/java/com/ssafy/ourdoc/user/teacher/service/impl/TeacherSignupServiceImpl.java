package com.ssafy.ourdoc.user.teacher.service.impl;

import com.ssafy.ourdoc.global.enums.UserType;
import com.ssafy.ourdoc.user.entity.User;
import com.ssafy.ourdoc.user.repository.UserRepository;
import com.ssafy.ourdoc.user.teacher.dto.TeacherSignupRequest;
import com.ssafy.ourdoc.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.user.teacher.service.interf.TeacherSignupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherSignupServiceImpl implements TeacherSignupService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    // 1. 교사 회원가입
    @Override
    public Long signup(TeacherSignupRequest request) {

        // 중복 ID 체크
        Optional<User> existingUser = userRepository.findByLoginId(request.getLoginId());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 로그인ID입니다.");
        }

        // 1) User 엔티티 생성
        User user = User.builder()
                .userType(UserType.교사)
                .name(request.getName())
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .birth(request.getBirth())
                .gender(request.getGender())
                .active(request.getActive())
                .build();

        User savedUser = userRepository.save(user);

        // 2) Teacher 엔티티 생성
        Teacher teacher = Teacher.builder()
                .user(savedUser)
                .email(request.getEmail())
                .phone(request.getPhone())
//                .employmentStatus(request.getEmploymentStatus())
//                .certificateTime(request.getCertificateTime())
                .build();

        Teacher savedTeacher = teacherRepository.save(teacher);
        return savedTeacher.getId();
    }
}
