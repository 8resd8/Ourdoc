package com.ssafy.ourdoc.domain.award.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherCreateRequest;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherDto;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherRequest;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherResponse;
import com.ssafy.ourdoc.domain.award.entity.Award;
import com.ssafy.ourdoc.domain.award.repository.AwardRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.domain.user.repository.UserRepository;
import com.ssafy.ourdoc.global.integration.s3.service.S3StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AwardTeacherService {

	private final AwardRepository awardRepository;
	private final S3StorageService s3StorageService;
	private final UserRepository userRepository;

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
		String studentLoginId = request.studentLoginId();
		User studentUser = userRepository.findByLoginId(studentLoginId)
			.orElseThrow(() -> new NoSuchElementException("해당 학생이 없습니다."));
		List<AwardTeacherDto> teacherClassAwards = awardRepository.findTeacherClassAwards(user.getId(), request,
			studentUser.getId());
		return new AwardTeacherResponse(teacherClassAwards);
		// return new AwardTeacherResponse(awardRepository.findTeacherClassAwards(user.getId(), request, studentUser.getId()) );
	}
}
