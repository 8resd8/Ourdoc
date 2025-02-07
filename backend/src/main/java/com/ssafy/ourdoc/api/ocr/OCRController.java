package com.ssafy.ourdoc.api.ocr;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.global.integration.ocr.dto.HandOCRResponse;
import com.ssafy.ourdoc.global.integration.ocr.service.OCRService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ocr")
public class OCRController {

	private final OCRService ocrService;

	@PostMapping("/hand")
	@ResponseStatus(OK)
	public HandOCRResponse imageOcr(@RequestPart(value = "hand_image") MultipartFile handImageRequest) {
		return ocrService.handOCRConvert(handImageRequest);
	}

	@PostMapping
	@ResponseStatus(OK)
	public HandOCRResponse handOCRTest(
		@RequestPart(value = "hand_image", required = false) MultipartFile handImageRequest) {
		return ocrService.handOCRConvert(handImageRequest);
	}
}
