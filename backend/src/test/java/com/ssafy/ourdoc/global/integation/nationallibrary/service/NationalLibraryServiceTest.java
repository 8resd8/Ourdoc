package com.ssafy.ourdoc.global.integation.nationallibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.global.integration.nationallibrary.dto.NationalLibraryBookResponse;
import com.ssafy.ourdoc.global.integration.nationallibrary.service.NationalLibraryBookService;

@SpringBootTest
class NationalLibraryServiceTest {

	@Autowired
	private NationalLibraryBookService service;

	@Test
	@DisplayName("책 제목으로 검색")
	void searchNLBooksByTitleTest() throws IOException {
		// 책 제목으로 검색
		Map<String, String> params = new HashMap<>();
		params.put("title", "어린왕자");

		// 서비스 호출
		List<NationalLibraryBookResponse> books = service.parseBook(BookRequest.builder()
			.title(params.get("title"))
			.build());

		System.out.println("검색된 책: " + books.get(0));
	}

	@Test
	@DisplayName("저자 이름으로 검색")
	void searchNLBooksByAuthorTest() throws IOException {
		// 저자 이름으로 검색
		Map<String, String> params = new HashMap<>();
		params.put("author", "생텍쥐페리");

		// 서비스 호출
		List<NationalLibraryBookResponse> books = service.parseBook(BookRequest.builder()
			.author(params.get("author"))
			.build());

		System.out.println("검색된 책: " + books.get(0));
	}

	@Test
	@DisplayName("출판사로 검색")
	void searchNLBooksByPublisherTest() throws IOException {
		// 출판사로 검색
		Map<String, String> params = new HashMap<>();
		params.put("publisher", "문예출판사");

		// 서비스 호출
		List<NationalLibraryBookResponse> books = service.parseBook(BookRequest.builder()
			.publisher(params.get("publisher"))
			.build());

		System.out.println("검색된 책: " + books.get(0));
	}

	@Test
	@DisplayName("제목, 저자, 출판사로 검색")
	void searchNLBooksByTitleAuthorPublisherTest() throws IOException {
		// 제목, 저자, 출판사로 검색
		Map<String, String> params = new HashMap<>();
		params.put("title", "어린왕자");
		params.put("author", "생텍쥐페리");
		params.put("publisher", "문예출판사");

		// 서비스 호출
		List<NationalLibraryBookResponse> books = service.parseBook(BookRequest.builder()
			.title(params.get("title"))
			.author(params.get("author"))
			.publisher(params.get("publisher"))
			.build());

		System.out.println("검색된 책: " + books.get(0));
	}

	@Test
	@DisplayName("검색 결과에 해당하는 책이 없음")
	void searchNoSuchBooksTest() throws IOException {
		// 제목으로 검색
		Map<String, String> params = new HashMap<>();
		params.put("title", "세상에서가장재미있는책이있다면너에게줄게");

		BookRequest request = BookRequest.builder()
			.title(params.get("title"))
			.build();

		assertEquals(0, service.parseBook(request).size());
	}
}