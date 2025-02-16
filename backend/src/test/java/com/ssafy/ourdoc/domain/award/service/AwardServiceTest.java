package com.ssafy.ourdoc.domain.award.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.award.dto.AwardDto;
import com.ssafy.ourdoc.domain.award.dto.AwardListResponse;
import com.ssafy.ourdoc.domain.award.dto.teacher.AwardTeacherCreateRequest;
import com.ssafy.ourdoc.domain.award.entity.Award;
import com.ssafy.ourdoc.domain.award.repository.AwardRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.integration.s3.service.S3StorageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class AwardServiceTest {

	@Mock
	private AwardRepository awardRepository;

	@Mock
	private S3StorageService s3StorageService;

	@InjectMocks
	private AwardService awardService;

	@InjectMocks
	private AwardTeacherService awardTeacherService;

	@InjectMocks
	private AwardStudentService awardStudentService;

	private Award award;
	private User user;

	@BeforeEach
	void setUp() {
		user = UserSample.user(UserType.학생);

		award = Award.builder()
			.title("우수상")
			.imagePath("s3://bucket/award.png")
			.build();
	}

	@Test
	@DisplayName("상장 생성 테스트")
	void createAward() {
		AwardTeacherCreateRequest request = new AwardTeacherCreateRequest("우수상");
		MultipartFile mockFile = mock(MultipartFile.class);

		when(s3StorageService.uploadFile(mockFile)).thenReturn("s3://bucket/award.png");
		when(awardRepository.save(any(Award.class))).thenReturn(award);

		awardTeacherService.createAward(user, request, mockFile);

		verify(s3StorageService, times(1)).uploadFile(mockFile);
		verify(awardRepository, times(1)).save(any(Award.class));
	}

	@Test
	@DisplayName("전체 상장 조회 테스트")
	void getAllAwards() {
		List<AwardDto> awardDtoList = List.of(new AwardDto(1L, "s3://bucket/award.png", "우수상", LocalDateTime.now()));

		when(awardRepository.findAllAwardByUserId(user.getId())).thenReturn(awardDtoList);

		AwardListResponse response = awardStudentService.getAllAwards(user);

		assertThat(response.awards()).isNotEmpty();
		assertThat(response.awards().get(0).title()).isEqualTo("우수상");
		verify(awardRepository, times(1)).findAllAwardByUserId(user.getId());
	}

	@Test
	@DisplayName("상장 단건 조회 테스트")
	void awardDetail() {
		// given
		AwardDto awardDto = new AwardDto(1L, "s3://bucket/award.png", "우수상", LocalDateTime.now());

		// Mocking: awardRepository.findAwardByUserId 호출 시 awardDto 반환
		when(awardRepository.findAwardByUserId(user.getId(), 1L)).thenReturn(Optional.of(awardDto));

		// when
		AwardDto result = awardService.awardDetail(user, 1L);

		// then
		assertThat(result).isNotNull();
		assertThat(result.title()).isEqualTo("우수상");
		verify(awardRepository, times(1)).findAwardByUserId(user.getId(), 1L);
	}

	@Test
	@DisplayName("존재하지 않는 상장 조회 시 예외 발생")
	void awardDetail_NotFound() {
		when(awardRepository.findAwardByUserId(user.getId(), 999L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> awardService.awardDetail(user, 999L))
			.isInstanceOf(NoSuchElementException.class);

		verify(awardRepository, times(1)).findAwardByUserId(user.getId(), 999L);
	}

}