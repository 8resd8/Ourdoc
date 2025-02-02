package com.ssafy.ourdoc.global.integration.nationallibrary.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.global.common.enums.KDC;
import com.ssafy.ourdoc.global.integration.nationallibrary.dto.NationalLibraryBookResponse;
import com.ssafy.ourdoc.global.integration.nationallibrary.exception.NationalLibraryBookFailException;

@Service
public class NationalLibraryBookService {
	@Value("${book.api-url}")
	private String apiUrl;

	@Value("${book.cert-key}")
	private String certKey;

	/**
	 * Parse book list.
	 *
	 * @param nationalLibraryBookRequest title(제목), author(저자), publisher(출판사)로 구성된 JSON
	 * @return bookList 국립중앙도서관에서 검색된 book 결과 정보
	 */
	public List<NationalLibraryBookResponse> parseBook(BookRequest nationalLibraryBookRequest) {
		try {
			Map<String, String> params = buildQueryParams(nationalLibraryBookRequest);
			String response = getHttpResponse(params);
			return parseBooksFromResponse(response);
		} catch (Exception e) {
			throw new NationalLibraryBookFailException("국립중앙도서관 API 호출 중 오류 발생");
		}
	}

	private Map<String, String> buildQueryParams(BookRequest nationalLibraryBookRequest) {
		String title = Optional.ofNullable(nationalLibraryBookRequest.title()).orElse("");
		String author = Optional.ofNullable(nationalLibraryBookRequest.author()).orElse("");
		String publisher = Optional.ofNullable(nationalLibraryBookRequest.publisher()).orElse("");

		return Map.of(
			"title", title,
			"author", author,
			"publisher", publisher
		);
	}

	private String getHttpResponse(Map<String, String> params) throws IOException {
		HttpURLConnection conn = getConnection(params);
		StringBuilder response = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
			conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300 ? conn.getInputStream() :
				conn.getErrorStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
		}

		conn.disconnect();
		return response.toString();
	}

	private HttpURLConnection getConnection(Map<String, String> params) {
		try {
			String queryString = buildQueryString(params);
			URL url = new URL(apiUrl + "?" + queryString);

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			return conn;
		} catch (IOException e) {
			throw new NationalLibraryBookFailException("국립중앙도서관 API 연결 중 오류 발생");
		}
	}

	private String buildQueryString(Map<String, String> params) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("cert_key=").append(URLEncoder.encode(certKey, "UTF-8"))
				.append("&result_style=").append(URLEncoder.encode("json", "UTF-8"))
				.append("&page_no=").append(URLEncoder.encode("1", "UTF-8"))
				.append("&page_size=").append(URLEncoder.encode("1000", "UTF-8"));

			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append("&")
					.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append("=")
					.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			throw new NationalLibraryBookFailException("쿼리 문자열 인코딩 중 오류 발생");
		}
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
			LocalDate publishTime = convertDate(docObject.getString("PUBLISH_PREDATE"));
			String imageUrl = docObject.getString("TITLE_URL");

			bookList.add(
				new NationalLibraryBookResponse(isbn, bookTitle, bookAuthor, genre, description, bookPublisher,
					publishTime, imageUrl));
		}
		return bookList;
	}

	private LocalDate convertDate(String date) {
		if (date == null || date.isEmpty()) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.parse(date, formatter);
	}
}
