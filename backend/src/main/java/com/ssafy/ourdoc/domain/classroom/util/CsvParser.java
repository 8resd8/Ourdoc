package com.ssafy.ourdoc.domain.classroom.util;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.ssafy.ourdoc.domain.classroom.dto.SchoolDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CsvParser {
	public List<SchoolDto> parseSchoolCsv(String filePath) {
		List<SchoolDto> result = new ArrayList<>();

		try (CSVReader reader = new CSVReader(
			new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

			String[] line;
			boolean isHeader = true;

			while ((line = reader.readNext()) != null) {
				// 헤더 스킵
				if (isHeader) {
					isHeader = false;
					continue;
				}

				// CSV 컬럼 순서에 맞추어 인덱스 지정
				String schoolName = line[3];     // 학교명 (예: 경기초등학교)
				String schoolType = line[5];     // 학교종류명 (예: 초등학교)
				String address = line[10];       // 도로명주소 (예: 서울특별시 서대문구 경기대로9길 10)

				if ("초등학교".equals(schoolType)) {
					SchoolDto dto = new SchoolDto(schoolName, address);
					result.add(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}