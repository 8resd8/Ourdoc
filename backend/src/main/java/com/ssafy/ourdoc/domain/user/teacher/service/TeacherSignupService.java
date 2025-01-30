package com.ssafy.ourdoc.domain.user.teacher.service;

import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.domain.user.teacher.dto.TeacherSignupRequest;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherSignupService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    // 1. 교사 회원가입
    public Long signup(TeacherSignupRequest request) {

        // 1) 중복 ID 체크
        Optional<User> existingUser = userRepository.findByLoginId(request.getLoginId());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 로그인ID입니다.");
        }

        // 2) 비밀번호 해싱
        String encodedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        // 3) User 엔티티 생성
        User user = User.builder()
                .userType(UserType.교사)
                .name(request.getName())
                .loginId(request.getLoginId())
                .password(encodedPassword)
                .birth(request.getBirth())
                .gender(request.getGender())
                .active(request.getActive())
                .build();

        User savedUser = userRepository.save(user);

        // 4) Teacher 엔티티 생성
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
