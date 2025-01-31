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
import com.ssafy.ourdoc.global.integration.s3.S3StorageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AwardService {

	private final AwardRepository awardRepository;
	private final S3StorageService s3StorageService;

	public void createAward(CreateAwardRequest request, MultipartFile file) {
		String imagePath = s3StorageService.uploadFile(file);

		Award award = Award.builder().title(request.title()).user(null) // user정보 필요함.
			.imagePath(imagePath).build();

		awardRepository.save(award);
	}

	public AwardListResponse getAllAward() {
		List<AwardDto> allAward = awardRepository.findAllAward();
		return new AwardListResponse(allAward);
	}

	public AwardDto searchAward(Long awardId) {
		Award award = getFindAward(awardId);
		return new AwardDto(award.getId(), award.getImagePath(), award.getTitle(), award.getCreatedAt());
	}

	private Award getFindAward(Long id) {
		return awardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당하는 상장이 없습니다."));
	}
}
