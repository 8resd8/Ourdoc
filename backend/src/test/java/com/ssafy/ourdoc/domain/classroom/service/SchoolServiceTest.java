package com.ssafy.ourdoc.domain.classroom.service;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.ourdoc.domain.classroom.dto.SchoolResponse;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.service.SchoolService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
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

	@Test
	void searchDBSchoolName() {
		School school = schoolService.searchSchoolName("경기");

		assertThat(school).isNotNull();
		assertThat(school.getSchoolName()).isEqualTo("경기초등학교");
	}

}