package com.ssafy.ourdoc.global.integration.ocr.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.global.util.Prompt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OCREnhancerService {

	private final ChatModel chatModel;

	public String enhanceText(String text) {
		if (text == null || text.isEmpty()) {
			return "";
		}
		return chatModel.call(Prompt.ocrEnhancer(text));
	}
}