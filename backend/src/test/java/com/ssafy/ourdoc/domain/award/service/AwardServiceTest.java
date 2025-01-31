package com.ssafy.ourdoc.domain.award.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.ssafy.ourdoc.domain.award.dto.AwardDto;
import com.ssafy.ourdoc.domain.award.dto.AwardListResponse;
import com.ssafy.ourdoc.domain.award.dto.CreateAwardRequest;
import com.ssafy.ourdoc.domain.award.entity.Award;
import com.ssafy.ourdoc.domain.award.repository.AwardRepository;
import com.ssafy.ourdoc.global.integration.s3.S3StorageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

@ExtendWith(MockitoExtension.class)
class AwardServiceTest {

	@Mock
	private AwardRepository awardRepository;

	@Mock
	private S3StorageService s3StorageService;

	@InjectMocks
	private AwardService awardService;

	private Award award;

	@BeforeEach
	void setUp() {
		award = Award.builder()
			.title("우수상")
			.imagePath("s3://bucket/award.png")
			.build();
	}

	@Test
	@DisplayName("상장 생성 테스트")
	void createAward() {
		CreateAwardRequest request = new CreateAwardRequest("우수상");
		MultipartFile mockFile = mock(MultipartFile.class);

		when(s3StorageService.uploadFile(mockFile)).thenReturn("s3://bucket/award.png");
		when(awardRepository.save(any(Award.class))).thenReturn(award);

		awardService.createAward(request, mockFile);

		verify(s3StorageService, times(1)).uploadFile(mockFile);
		verify(awardRepository, times(1)).save(any(Award.class));
	}

	@Test
	@DisplayName("전체 상장 조회 테스트")
	void getAllAward() {
		List<AwardDto> awardDtoList = List.of(new AwardDto(1L, "s3://bucket/award.png", "우수상", LocalDateTime.now()));

		when(awardRepository.findAllAward()).thenReturn(awardDtoList);

		AwardListResponse response = awardService.getAllAward();

		assertThat(response.awards()).isNotEmpty();
		assertThat(response.awards().get(0).title()).isEqualTo("우수상");
		verify(awardRepository, times(1)).findAllAward();
	}

	@Test
	@DisplayName("상장 단건 조회 테스트")
	void searchAward() {
		when(awardRepository.findById(1L)).thenReturn(Optional.of(award));

		AwardDto result = awardService.searchAward(1L);

		assertThat(result).isNotNull();
		assertThat(result.title()).isEqualTo("우수상");
		verify(awardRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("존재하지 않는 상장 조회 시 예외 발생")
	void searchAward_NotFound() {
		when(awardRepository.findById(405L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> awardService.searchAward(405L))
			.isInstanceOf(NoSuchElementException.class)
			.hasMessage("해당하는 상장이 없습니다.");
	}
}
