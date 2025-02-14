package com.ssafy.ourdoc.global.integation.nationallibrary.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.ourdoc.global.integration.nationallibrary.service.NationalLibraryBookService;

@SpringBootTest
class NationalLibraryServiceTest {

	@Autowired
	private NationalLibraryBookService nationalLibraryBookService;

	// @Test
	// @DisplayName("국립중앙도서관의 보유 도서 수를 가져온다.")
	// void getNationalLibraryBookCountTest() {
	// 	assertThat(nationalLibraryBookService.getBookCount()).isGreaterThan(0);
	// }
}