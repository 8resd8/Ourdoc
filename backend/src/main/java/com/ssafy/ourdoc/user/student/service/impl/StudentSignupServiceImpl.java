package com.ssafy.ourdoc.user.student.service.impl;

import com.ssafy.ourdoc.global.enums.UserType;
import com.ssafy.ourdoc.user.entity.User;
import com.ssafy.ourdoc.user.repository.UserRepository;
import com.ssafy.ourdoc.user.student.dto.StudentSignupRequest;
import com.ssafy.ourdoc.user.student.entity.Student;
import com.ssafy.ourdoc.user.student.repository.StudentRepository;
import com.ssafy.ourdoc.user.student.service.interf.StudentSignupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentSignupServiceImpl implements StudentSignupService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    // 1. 학생 회원가입
    @Override
    public Long signup(StudentSignupRequest request) {
        // 아이디 중복 체크
        Optional<User> existingUser = userRepository.findByLoginId(request.getLoginId());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 로그인 ID입니다.");
        }

        // User 생성
        User user = User.builder()
                .userType(UserType.학생)
                .name(request.getName())
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .birth(request.getBirth())
                .gender(request.getGender())
                .active(request.getActive())
                .build();
        User savedUser = userRepository.save(user);

        // Student 생성
        Student student = Student.builder()
                .user(savedUser)
//                .tempPassword(request.getTempPassword())
//                .authStatus(request.getAuthStatus())
//                .certificateTime(request.getCertificateTime())
                .build();
        Student savedStudent = studentRepository.save(student);

        return savedStudent.getId();
    }
}
