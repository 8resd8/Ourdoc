package com.ssafy.ourdoc.domain.classroom.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.classroom.dto.SchoolResponse;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolService {
	@Value("${school.api-url}")
	private String apiUrl;

	@Value("${school.api-key}")
	private String apiKey;

	private final SchoolRepository schoolRepository;

	public Page<SchoolResponse> searchSchoolName(String schoolName, Pageable pageable) {
		Page<School> schoolPage = schoolRepository.findAllBySchoolNameContaining(schoolName, pageable);

		return schoolPage.map(school ->
			new SchoolResponse(
				school.getSchoolName(),
				school.getAddress()
			)
		);
	}

	// 학교 API 검색
	public List<SchoolResponse> parseSchool(String schoolName) {
		String response = null;
		try {
			response = getHttpResponse(schoolName);
		} catch (IOException e) {
			throw new RuntimeException("학교검색 API에 문제가 생겼습니다.", e);
		}
		List<SchoolResponse> schoolList = parseBooksFromResponse(response);
		return schoolList;
	}

	private String getHttpResponse(String params) throws IOException {
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

	private HttpURLConnection getConnection(String params) throws IOException {
		String queryString = buildQueryString(params);
		URL url = new URL(apiUrl + "?" + queryString);

		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		return conn;
	}

	private String buildQueryString(String schoolName) throws UnsupportedEncodingException {
		if (schoolName == null || schoolName.isEmpty()) {
			schoolName = "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("KEY=")
			.append(URLEncoder.encode(apiKey, "UTF-8"))
			.append("&Type=")
			.append(URLEncoder.encode("json", "UTF-8"))
			.append("&pIndex=")
			.append(URLEncoder.encode("1", "UTF-8"))
			.append("&pSize=")
			.append(URLEncoder.encode("1000", "UTF-8"))
			.append("&SCHUL_KND_SC_NM=")
			.append(URLEncoder.encode("초등학교", "UTF-8"))
			.append("&SCHUL_NM=")
			.append(URLEncoder.encode(schoolName, "UTF-8"));

		return sb.toString();
	}

	private List<SchoolResponse> parseBooksFromResponse(String response) {
		List<SchoolResponse> schoolList = new ArrayList<>();
		JSONObject respJson = new JSONObject(response);
		JSONArray schoolArr = respJson.getJSONArray("schoolInfo").getJSONObject(1).getJSONArray("row");
		for (int i = 0; i < schoolArr.length(); i++) {
			JSONObject schoolObject = schoolArr.getJSONObject(i);
			String schoolName = schoolObject.getString("SCHUL_NM");
			String address = schoolObject.getString("ORG_RDNMA");

			schoolList.add(new SchoolResponse(schoolName, address));
		}
		return schoolList;
	}
}
