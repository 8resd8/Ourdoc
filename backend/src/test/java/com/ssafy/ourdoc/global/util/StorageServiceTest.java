package com.ssafy.ourdoc.global.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.ssafy.ourdoc.global.common.S3StorageService;

@SpringBootTest
class StorageServiceTest {

	@Autowired
	private S3StorageService s3StorageService;

	@Test
	@DisplayName("업로드 결과 확인")
	void testUploadFile() throws Exception {
		MockMultipartFile mockFile = new MockMultipartFile(
			"file",
			"test-image.png",
			"image/png",
			new byte[] {1, 2, 3, 4}  // 간단한 바이트 배열
		);

		String uploadedUrl = s3StorageService.uploadFile(mockFile);

		// 업로드된 결과가 null 또는 빈 문자열인지 확인
		assertNotNull(uploadedUrl);
		assertFalse(uploadedUrl.isEmpty());
	}
}