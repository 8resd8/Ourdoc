package com.ssafy.ourdoc.global.integration.ocr.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.global.integration.ocr.dto.HandOCRResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OCRService {

	private final OCRBasicService ocrBasicService;
	private final OCREnhancerService ocrEnhancerService;

	public HandOCRResponse handOCRConvert(MultipartFile file) {
		String basicText = ocrBasicService.OCRConvert(file);
		String enhancedTest = ocrEnhancerService.enhanceText(basicText);

		return new HandOCRResponse(enhancedTest);
	}
}