package com.ssafy.ourdoc.global.integration.read.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.book.service.BookService;
import com.ssafy.ourdoc.global.integration.nationallibrary.exception.NationalLibraryBookFailException;
import com.ssafy.ourdoc.global.integration.read.dto.ReadBookResponse;
import com.ssafy.ourdoc.global.util.DateConvertor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReadService {

	@Value("${book.read-url}")
	private String readUrl;

	private final int PAGE_SIZE = 100;

	private final BookService bookService;

	private final BookRepository bookRepository;

	public void parseBook() {
		try {
			int totalCount = getBookCount();
			log.info("독서로 도서 totalCount: {}", totalCount);
			int totalPageCount = (int)Math.ceil((double)totalCount / PAGE_SIZE);

			for (int pageNo = 1; pageNo <= totalPageCount; pageNo++) {
				log.info("독서로 도서 가져오는 중: {} / {}", pageNo, totalPageCount);
				parseBookByPage(pageNo);
				Thread.sleep(5000); // API 호출 간격 조절
			}
		} catch (Exception e) {
			throw new NationalLibraryBookFailException("독서로 API 호출 중 오류 발생");
		}
	}

	private void parseBookByPage(int pageNo) {
		String response = getHttpResponse(pageNo);
		List<ReadBookResponse> bookResponses = parseBooksFromResponse(response);

		if (bookResponses.isEmpty()) {
			log.info("페이지 {} 에서 도서 데이터 없음", pageNo);
			return;
		}

		List<ReadBookResponse> uniqueBooks = bookResponses.stream()
			.filter(distinctByKey(ReadBookResponse::isbn))
			.toList();

		List<String> isbns = uniqueBooks.stream()
			.map(ReadBookResponse::isbn)
			.collect(Collectors.toList());

		List<String> existingIsbns = bookRepository.findByIsbnIn(isbns)
			.stream()
			.map(Book::getIsbn)
			.toList();

		List<Book> newBooks = uniqueBooks.stream()
			.filter(responseObj -> !existingIsbns.contains(responseObj.isbn()))
			.map(ReadBookResponse::toBookEntity)
			.collect(Collectors.toList());

		log.info("페이지 {}에서 신규 도서 {}개 확인", pageNo, newBooks.size());

		if (!newBooks.isEmpty()) {
			bookService.registerBookList(newBooks);
			log.info("페이지 {}에서 도서 {}개 저장 완료", pageNo, newBooks.size());
		}
	}

	private String getHttpResponse(int pageNo) {
		HttpURLConnection conn = getConnection(pageNo);
		StringBuilder response = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
			conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300 ? conn.getInputStream() :
				conn.getErrorStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
		} catch (IOException e) {
			throw new NationalLibraryBookFailException("독서로 API 호출 중 오류 발생");
		}

		conn.disconnect();
		return response.toString();
	}

	private HttpURLConnection getConnection(int pageNo) {
		try {
			URL url = new URL(readUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true); // POST 요청에서 본문을 보낼 수 있도록 설정

			// 요청 본문 JSON 생성
			JSONObject requestBody = new JSONObject();
			requestBody.put("categoryCode", "022000000");
			requestBody.put("kdcCode", "");
			requestBody.put("provCode", "B10");
			requestBody.put("schoolName", "");
			requestBody.put("display", PAGE_SIZE);
			requestBody.put("page", pageNo);

			// JSON 데이터를 요청 본문에 쓰기
			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = requestBody.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
				os.flush();
			}

			return conn;
		} catch (IOException e) {
			throw new NationalLibraryBookFailException("독서로 API 연결 중 오류 발생");
		}
	}

	public int getBookCount() {
		String response = getHttpResponse(1);
		JSONObject respJson = new JSONObject(response);
		JSONObject data = respJson.getJSONObject("data");
		return data.getInt("allTotalCount");
	}

	private List<ReadBookResponse> parseBooksFromResponse(String response) {
		List<ReadBookResponse> bookList = new ArrayList<>();
		JSONObject respJson = new JSONObject(response);
		JSONObject data = respJson.getJSONObject("data");
		JSONArray books = data.getJSONArray("bookList");

		for (int i = 0; i < books.length(); i++) {
			JSONObject bookObject = books.getJSONObject(i);
			String isbn = bookObject.getString("isbn");
			String bookTitle = bookObject.getString("title");
			String bookAuthor = bookObject.getString("author");
			String genre = bookObject.getJSONObject("kdcInfo").getString("ldesc");
			String description = "";
			String bookPublisher = bookObject.getString("publisher");
			LocalDate publishTime = DateConvertor.convertDate(bookObject.getString("pubYear") + "0101");
			String imageUrl = "";
			if (bookObject.getString("coverYn").equals("Y")) {
				imageUrl = bookObject.getString("coverUrl");
			}

			bookList.add(
				new ReadBookResponse(isbn, bookTitle, bookAuthor, genre, description, bookPublisher,
					publishTime, imageUrl));
		}
		return bookList;
	}

	private static <T> Predicate<T> distinctByKey(Function<T, Object> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}
}
