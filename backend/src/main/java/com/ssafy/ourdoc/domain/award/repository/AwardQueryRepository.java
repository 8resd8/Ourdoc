package com.ssafy.ourdoc.domain.award.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.ourdoc.domain.award.dto.AwardDto;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherDto;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherRequest;

public interface AwardQueryRepository {

	List<AwardDto> findAllAwardByUserId(Long userId);

	Optional<AwardDto> findAwardByUserId(Long userId, Long awardId);

	List<AwardTeacherDto> findTeacherClassAwards(Long teacherId, AwardTeacherRequest request);
}
