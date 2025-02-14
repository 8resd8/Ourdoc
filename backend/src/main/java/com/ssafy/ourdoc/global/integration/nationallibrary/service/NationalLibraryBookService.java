package com.ssafy.ourdoc.global.integration.nationallibrary.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Year;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.book.service.BookService;
import com.ssafy.ourdoc.global.common.enums.KDC;
import com.ssafy.ourdoc.global.integration.nationallibrary.dto.NationalLibraryBookResponse;
import com.ssafy.ourdoc.global.integration.nationallibrary.exception.NationalLibraryBookFailException;
import com.ssafy.ourdoc.global.util.DateConvertor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NationalLibraryBookService {
	@Value("${book.api-url}")
	private String apiUrl;

	@Value("${book.cert-key}")
	private String certKey;

	private final int PAGE_SIZE = 1000;

	private final BookService bookService;

	private final BookRepository bookRepository;

	@Scheduled(cron = "0 0 3 * * *")
	public void updateBookListFromNationalLibrary() {
		List<NationalLibraryBookResponse> externalBooks = parseBook();
		log.info("국립중앙도서관 도서 {} 개 수집 완료", externalBooks.size());

		List<NationalLibraryBookResponse> uniqueBooks = externalBooks.stream()
			.filter(distinctByKey(NationalLibraryBookResponse::isbn))
			.toList();
		log.info("국립중앙도서관 내 ISBN 중복 제외 도서 {} 개 확인", uniqueBooks.size());

		List<String> isbns = uniqueBooks.stream()
			.map(NationalLibraryBookResponse::isbn)
			.collect(Collectors.toList());

		List<String> existingIsbns = bookRepository.findByIsbnIn(isbns)
			.stream()
			.map(Book::getIsbn)
			.toList();

		List<Book> newBooks = uniqueBooks.stream()
			.filter(response -> !existingIsbns.contains(response.isbn()))
			.map(NationalLibraryBookResponse::toBookEntity)
			.collect(Collectors.toList());

		log.info("신규 도서 {} 개 확인", newBooks.size());

		if (!newBooks.isEmpty()) {
			bookService.registerBookList(newBooks);
			log.info("국립중앙도서관 도서 {} 개 저장 완료", newBooks.size());
		}
	}

	private List<NationalLibraryBookResponse> parseBook() {
		List<NationalLibraryBookResponse> allBooks = new ArrayList<>();

		try {
			int totalCount = getBookCount();
			log.info("국립중앙도서관 도서 totalCount: {}", totalCount);
			int totalPageCount = (int)Math.ceil((double)totalCount / PAGE_SIZE);

			for (int pageNo = 1; pageNo <= totalPageCount; pageNo++) {
				log.info("국립중앙도서관 도서 가져오는 중: {} / {}", pageNo, totalPageCount);
				String response = getHttpResponse(pageNo);
				allBooks.addAll(parseBooksFromResponse(response));
				Thread.sleep(5000);
			}

			return allBooks;
		} catch (Exception e) {
			throw new NationalLibraryBookFailException("국립중앙도서관 API 호출 중 오류 발생");
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
			throw new NationalLibraryBookFailException("국립중앙도서관 API 호출 중 오류 발생");
		}

		conn.disconnect();
		return response.toString();
	}

	private HttpURLConnection getConnection(int pageNo) {
		try {
			String queryString = buildQueryString(pageNo);
			URL url = new URL(apiUrl + "?" + queryString);

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			return conn;
		} catch (IOException e) {
			throw new NationalLibraryBookFailException("국립중앙도서관 API 연결 중 오류 발생");
		}
	}

	private String buildQueryString(int pageNo) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("cert_key=").append(URLEncoder.encode(certKey, "UTF-8"))
				.append("&result_style=").append(URLEncoder.encode("json", "UTF-8"))
				.append("&page_no=").append(URLEncoder.encode(String.valueOf(pageNo), "UTF-8"))
				.append("&page_size=").append(URLEncoder.encode(String.valueOf(PAGE_SIZE), "UTF-8"));

			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			throw new NationalLibraryBookFailException("쿼리 문자열 인코딩 중 오류 발생");
		}
	}

	public int getBookCount() {
		String response = getHttpResponse(1);
		JSONObject respJson = new JSONObject(response);
		return respJson.getInt("TOTAL_COUNT");
	}

	private List<NationalLibraryBookResponse> parseBooksFromResponse(String response) {
		List<NationalLibraryBookResponse> bookList = new ArrayList<>();
		JSONObject respJson = new JSONObject(response);
		JSONArray docsArr = respJson.getJSONArray("docs");

		for (int i = 0; i < docsArr.length(); i++) {
			JSONObject docObject = docsArr.getJSONObject(i);
			String isbn = docObject.getString("EA_ISBN");
			String bookTitle = docObject.getString("TITLE");
			String bookAuthor = docObject.getString("AUTHOR");
			String genre = KDC.fromCode(docObject.getString("SUBJECT"));
			String description = docObject.getString("BOOK_SUMMARY_URL");
			String bookPublisher = docObject.getString("PUBLISHER");
			Year publishYear = DateConvertor.convertYear(docObject.getString("PUBLISH_PREDATE"));
			String imageUrl = docObject.getString("TITLE_URL");

			bookList.add(
				new NationalLibraryBookResponse(isbn, bookTitle, bookAuthor, genre, description, bookPublisher,
					publishYear, imageUrl));
		}
		return bookList;
	}

	private static <T> Predicate<T> distinctByKey(Function<T, Object> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}
}
