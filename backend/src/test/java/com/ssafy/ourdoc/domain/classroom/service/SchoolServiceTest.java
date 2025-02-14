package com.ssafy.ourdoc.domain.classroom.service;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ssafy.ourdoc.domain.classroom.dto.SchoolResponse;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class SchoolServiceTest {

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private SchoolRepository schoolRepository;

	@Test
	@DisplayName("초등학교 이름으로 검색")
	void searchSchoolByName() throws IOException {
		List<SchoolResponse> schoolList;
		schoolList = schoolService.parseSchool("광명");
		System.out.println(schoolList);
	}

	@Test
	void searchDBSchoolName() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<School> school = schoolRepository.findAllBySchoolNameContaining("신중", pageRequest);

		assertThat(school).isNotNull();
		assertThat(school.getContent().get(0).getSchoolName()).isEqualTo("서울신중초등학교");
		assertThat(school.getContent().get(0).getAddress()).isEqualTo("서울특별시 서초구 남부순환로317길 15");
	}

}