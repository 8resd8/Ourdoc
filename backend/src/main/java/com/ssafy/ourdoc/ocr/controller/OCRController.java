package com.ssafy.ourdoc.ocr.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.ocr.dto.FeedbackResponse;
import com.ssafy.ourdoc.ocr.dto.HandOCRResponse;
import com.ssafy.ourdoc.ocr.dto.SpellingRequest;
import com.ssafy.ourdoc.ocr.service.AiFeedbackService;
import com.ssafy.ourdoc.ocr.service.OCRService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ocr")
public class OCRController {

	private final OCRService ocrService;
	private final AiFeedbackService aiFeedbackService;

	@PostMapping("/{studentId}/hand")
	@ResponseStatus(OK)
	public HandOCRResponse handOCR(@PathVariable("studentId") Long studentId,
		@RequestPart(value = "hand_image", required = false) MultipartFile handImageRequest) {
		return ocrService.handOCRConvert(handImageRequest);
	}

	@PostMapping("/{studentId}/spelling")
	public FeedbackResponse checkSpelling(@PathVariable("studentId") Long studentId,
		@RequestBody SpellingRequest request) {
		return aiFeedbackService.spelling(studentId, request);
	}

	@PostMapping("/{studentId}/feedback")
	public FeedbackResponse provideFeedback(@PathVariable("studentId") Long studentId,
		@RequestBody SpellingRequest request) {
		return aiFeedbackService.spelling(studentId, request);
	}

	@PostMapping
	@ResponseStatus(OK)
	public HandOCRResponse handOCRTest(
		@RequestPart(value = "hand_image", required = false) MultipartFile handImageRequest) {
		return ocrService.handOCRConvert(handImageRequest);
	}
}