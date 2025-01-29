package com.ssafy.ourdoc.user.controller;

import com.ssafy.ourdoc.user.dto.LoginRequest;
import com.ssafy.ourdoc.user.dto.LoginResponse;
import com.ssafy.ourdoc.user.service.interf.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * POST /users/signin
     * Body:
     * {
     *   "userType": "학생",
     *   "loginId": "test1234",
     *   "password": "test1234!"
     * }
     */
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = loginService.login(request);

        // resultCode = "401"이면 Unauthorized(401), 그 외는 200
        if ("401".equals(response.getResultCode())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
