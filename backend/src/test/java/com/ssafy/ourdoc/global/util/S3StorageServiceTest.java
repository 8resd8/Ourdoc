package com.ssafy.ourdoc.global.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import com.ssafy.ourdoc.global.common.S3StorageService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

class S3StorageServiceTest {

	@Test
	@DisplayName("정상 파일 업로드")
	void testUploadFile_Success() throws Exception {
		// given
		S3Client mockS3Client = Mockito.mock(S3Client.class);
		when(mockS3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
			.thenReturn(PutObjectResponse.builder().build());

		S3StorageService s3StorageService = new S3StorageService(mockS3Client);

		MockMultipartFile mockFile = new MockMultipartFile(
			"file",
			"test-image.png",
			"image/png",
			new byte[] {1, 2, 3, 4}
		);

		// when
		String resultUrl = s3StorageService.uploadFile(mockFile);

		// then
		verify(mockS3Client, times(1))
			.putObject(any(PutObjectRequest.class), any(RequestBody.class));

		assertNotNull(resultUrl);
		assertFalse(resultUrl.isEmpty());
		System.out.println("결과 URL = " + resultUrl);
	}

	@Test
	@DisplayName("빈 파일 업로드 시 null")
	void testUploadFile_EmptyFile() throws Exception {
		S3Client mockS3Client = Mockito.mock(S3Client.class);
		when(mockS3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
			.thenReturn(PutObjectResponse.builder().build());

		S3StorageService s3StorageService = new S3StorageService(mockS3Client);

		MockMultipartFile mockFile = new MockMultipartFile(
			"file",
			"empty.png",
			"image/png",
			new byte[] {}
		);

		String result = s3StorageService.uploadFile(mockFile);

		assertNull(result);

		// putObject가 호출되지 않았는지 확인
		verify(mockS3Client, never()).putObject(any(PutObjectRequest.class), any(RequestBody.class));
	}
}
