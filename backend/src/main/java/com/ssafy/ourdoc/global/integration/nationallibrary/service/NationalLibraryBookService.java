package com.ssafy.ourdoc.global.integration.nationallibrary.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.global.common.enums.KDC;
import com.ssafy.ourdoc.global.integration.nationallibrary.dto.NationalLibraryBookRequest;
import com.ssafy.ourdoc.global.integration.nationallibrary.dto.NationalLibraryBookResponse;

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
	public List<NationalLibraryBookResponse> parseBook(NationalLibraryBookRequest nationalLibraryBookRequest) throws
		IOException {
		Map<String, String> params = buildQueryParams(nationalLibraryBookRequest);
		String response = getHttpResponse(params);
		List<NationalLibraryBookResponse> bookList = parseBooksFromResponse(response);
		return bookList;
	}

	private Map<String, String> buildQueryParams(NationalLibraryBookRequest nationalLibraryBookRequest) {
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

	private HttpURLConnection getConnection(Map<String, String> params) throws IOException {
		String queryString = buildQueryString(params);
		URL url = new URL(apiUrl + "?" + queryString);

		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		return conn;
	}

	private String buildQueryString(Map<String, String> params) throws UnsupportedEncodingException {
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
			String bookPublisher = docObject.getString("PUBLISHER");
			String publishTime = docObject.getString("PUBLISH_PREDATE");
			String imageUrl = docObject.getString("TITLE_URL");

			bookList.add(new NationalLibraryBookResponse(isbn, bookTitle, bookAuthor, genre, bookPublisher, publishTime,
				imageUrl));
		}
		return bookList;
	}
}
