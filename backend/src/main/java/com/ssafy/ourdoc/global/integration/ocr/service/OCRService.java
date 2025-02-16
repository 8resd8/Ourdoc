package com.ssafy.ourdoc.global.integration.ocr.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.global.integration.ocr.dto.HandOCRResponse;
import com.ssafy.ourdoc.global.integration.s3.service.S3StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OCRService {

	private final OCRBasicService ocrBasicService;
	private final OCREnhancerService ocrEnhancerService;
	private final S3StorageService s3StorageService;

	public HandOCRResponse handOCRConvert(MultipartFile file) {
		String originContext = ocrBasicService.OCRConvert(file);
		String enhancerContext = ocrEnhancerService.enhanceText(originContext)
			.replace("\\\\n\\\\n", "\n")
			.replace("\\n\\n", "\n")
			.replace("\\\\n", "\n")
			.replace("\\n", "\n");
		String ocrImagePath = s3StorageService.uploadFile(file);

		return HandOCRResponse.builder()
			.originContent(originContext)
			.enhancerContent(enhancerContext)
			.ocrImagePath(ocrImagePath)
			.build();
	}
}