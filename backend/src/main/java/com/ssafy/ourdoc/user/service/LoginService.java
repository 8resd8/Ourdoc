package com.ssafy.ourdoc.user.service;

import com.ssafy.ourdoc.user.dto.LoginRequest;
import com.ssafy.ourdoc.user.dto.LoginResponse;
import com.ssafy.ourdoc.user.entity.User;
import com.ssafy.ourdoc.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest request) {
        // (1) 아이디 & 비밀번호로 User 조회
        Optional<User> existingUser = userRepository.findByLoginIdAndPassword(
                request.getLoginId(),
                request.getPassword()
        );

        // (2) 로그인 실패 시
        if (existingUser.isEmpty()) {
            return LoginResponse.builder()
                    .resultCode("401")
                    .message("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.")
                    .user(null)
                    .build();
        }

        User user = existingUser.get();

        // (3) userType 불일치 시 (선택적 체크)
        //     - DB에 저장된 userType이 "학생"인지, 또는 다른 값인지 확인
        if (!user.getUserType().toString().equals(request.getUserType())) {
            return LoginResponse.builder()
                    .resultCode("401")
                    .message("로그인 실패: 유저 타입이 일치하지 않습니다.")
                    .user(null)
                    .build();
        }

        // (4) 로그인 성공
        return LoginResponse.builder()
                .resultCode("200")
                .message("로그인 성공")
                .user(
                        LoginResponse.UserInfo.builder()
                                .id(user.getLoginId())
                                .name(user.getName())
                                .role(user.getUserType().toString())
                                .build()
                )
                .build();
    }
}
