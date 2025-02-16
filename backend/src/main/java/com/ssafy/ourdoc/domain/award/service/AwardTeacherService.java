package com.ssafy.ourdoc.domain.award.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherRequest;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherResponse;
import com.ssafy.ourdoc.domain.award.repository.AwardRepository;
import com.ssafy.ourdoc.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AwardTeacherService {

	private final AwardRepository awardRepository;

	public AwardTeacherResponse getAwardTeachersClass(User user, AwardTeacherRequest request) {
		return new AwardTeacherResponse(awardRepository.findTeacherClassAwards(user.getId(), request));
	}
}
