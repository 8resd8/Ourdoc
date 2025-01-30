package com.ssafy.ourdoc.classroom.service;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.ourdoc.domain.classroom.dto.SchoolResponse;
import com.ssafy.ourdoc.domain.classroom.service.SchoolService;

@SpringBootTest
class SchoolServiceTest {

	@Autowired
	private SchoolService schoolService;

	@Test
	@DisplayName("초등학교 이름으로 검색")
	void searchSchoolByName() throws IOException {
		List<SchoolResponse> schoolList;
		schoolList = schoolService.parseSchool("광명");
		System.out.println(schoolList);
	}
}