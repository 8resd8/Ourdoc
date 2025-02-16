package com.ssafy.ourdoc.domain.award.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherCreateRequest;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherRequest;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherResponse;
import com.ssafy.ourdoc.domain.award.entity.Award;
import com.ssafy.ourdoc.domain.award.repository.AwardRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.integration.s3.service.S3StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AwardTeacherService {

	private final AwardRepository awardRepository;
	private final S3StorageService s3StorageService;

	// 상장 생성
	public void createAward(User user, AwardTeacherCreateRequest request, MultipartFile file) {
		String imagePath = s3StorageService.uploadFile(file);

		Award award = Award.builder()
			.title(request.title())
			.user(user)
			.imagePath(imagePath)
			.build();

		awardRepository.save(award);
	}

	public AwardTeacherResponse getAwardTeachersClass(User user, AwardTeacherRequest request) {
		return new AwardTeacherResponse(awardRepository.findTeacherClassAwards(user.getId(), request));
	}
}
