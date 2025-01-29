package com.ssafy.ourdoc.user.service.interf;

import com.ssafy.ourdoc.user.dto.LoginRequest;
import com.ssafy.ourdoc.user.dto.LoginResponse;

public interface LoginService {
    /**
     * 로그인 처리
     * @param request LoginRequest (userType, loginId, password)
     * @return LoginResponse (resultCode, message, userInfo)
     */
    LoginResponse login(LoginRequest request);
}
