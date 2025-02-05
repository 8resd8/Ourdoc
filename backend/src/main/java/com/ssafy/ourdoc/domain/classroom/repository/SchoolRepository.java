package com.ssafy.ourdoc.domain.classroom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.classroom.entity.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
	List<School> findAllBySchoolNameContaining(String schoolName);

	School findBySchoolNameAndAddress(String schoolName, String address);

	Optional<School> findBySchoolName(String schoolName);
}
