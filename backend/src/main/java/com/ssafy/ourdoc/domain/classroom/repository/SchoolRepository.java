package com.ssafy.ourdoc.domain.classroom.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.classroom.entity.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
	Page<School> findAllBySchoolNameContaining(String schoolName, Pageable pageable);

	School findBySchoolNameAndAddress(String schoolName, String address);

	Optional<School> findBySchoolName(String schoolName);
}
