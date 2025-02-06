package com.ssafy.ourdoc.domain.classroom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.classroom.dto.SchoolDto;
import com.ssafy.ourdoc.domain.classroom.entity.School;
import com.ssafy.ourdoc.domain.classroom.repository.SchoolRepository;
import com.ssafy.ourdoc.domain.classroom.util.CsvParser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolSaveService {

	private final SchoolRepository schoolRepository;
	private final CsvParser csvParser;

	public void saveSchools(String csvFilePath) {
		List<SchoolDto> schoolDtoList = csvParser.parseSchoolCsv(csvFilePath);
		List<School> schools = schoolDtoList.stream()
			.map(dto -> School.builder()
				.schoolName(dto.schoolName())
				.address(dto.address())
				.build())
			.toList();

		schoolRepository.saveAll(schools);
	}
}
