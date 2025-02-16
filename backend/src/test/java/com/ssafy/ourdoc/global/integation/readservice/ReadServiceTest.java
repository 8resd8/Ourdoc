package com.ssafy.ourdoc.global.integation.readservice;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.ourdoc.global.integration.read.service.ReadService;

@SpringBootTest
public class ReadServiceTest {
	@Autowired
	private ReadService readService;

	@Test
	@Disabled
	@DisplayName("독서로의 보유 도서 수를 가져온다.")
	void getReadBookCountTest() {
		assertThat(readService.getBookCount()).isGreaterThan(0);
	}

	@Test
	@Disabled
	@DisplayName("독서로의 데이터 업로드")
	void uploadReadBookTest() {
		readService.parseBook();
	}
}
