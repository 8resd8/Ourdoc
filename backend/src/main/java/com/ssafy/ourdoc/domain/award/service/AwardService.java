package com.ssafy.ourdoc.domain.award.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.domain.award.dto.AwardDto;
import com.ssafy.ourdoc.domain.award.dto.AwardListResponse;
import com.ssafy.ourdoc.domain.award.dto.CreateAwardRequest;
import com.ssafy.ourdoc.domain.award.entity.Award;
import com.ssafy.ourdoc.domain.award.repository.AwardRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.integration.s3.service.S3StorageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AwardService {

	private final AwardRepository awardRepository;
	private final S3StorageService s3StorageService;

	// 상장 생성
	public void createAward(User user, CreateAwardRequest request, MultipartFile file) {
		String imagePath = s3StorageService.uploadFile(file);

		Award award = Award.builder()
			.title(request.title())
			.user(user)
			.imagePath(imagePath)
			.build();

		awardRepository.save(award);
	}

	// 학생 - 상장 전체조회
	public AwardListResponse getAllAwards(User user) {

		List<AwardDto> allAwards = awardRepository.findAllAwardByUserId(user.getId());
		return new AwardListResponse(allAwards);
	}

	// 학생 - 상장 상세조회
	public AwardDto awardDetail(User user, Long awardId) {
		return getFindAward(user.getId(), awardId);
	}

	private AwardDto getFindAward(Long userId, Long awardId) {
		return awardRepository.findAwardByUserId(userId, awardId)
			.orElseThrow(() -> new NoSuchElementException("상장이 없습니다."));
	}

}
