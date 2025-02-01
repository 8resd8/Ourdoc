package com.ssafy.ourdoc.domain.user.student.dto;

import java.sql.Date;

import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;

import lombok.Getter;

/**
 * 학생 회원가입 요청 DTO
 */
@Getter
public class StudentSignupRequest {
	// === User 엔티티에 필요한 필드 ===
	private String name;
	private String loginId;
	private String password;
	private String schoolName;
	private int grade;
	private int classNumber;
	private Date birth;
	private Gender gender;
	private Active active; // 활성/비활성
}
