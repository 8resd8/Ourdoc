package com.ssafy.ourdoc.global.util;

public class Prompt {

	// 독서록 피드백
	public static String feedback(int studentYear, String content) {
		return """
			아래 글은 초등학교 %d학년 학생이 작성한 독서록입니다.
			내용: [%s]
			
			2. 이 글을 읽고, %d학년 수준에서 이해할 수 있는 간단한 문장으로 독후감 내용에 대한 칭찬과 개선점을 균형 있게 제시해 주세요.
			예를 들어, 글의 주제 이해도, 감상 표현, 책에 대한 흥미 등을 고려해 총 1가지 피드백을 작성해 주세요.
			또한, 너무 어려운 개념이나 어휘는 사용하지 말고, 아이가 긍정적인 동기를 가질 수 있도록 부드럽게 표현해 주세요.
			""".formatted(studentYear, content, studentYear, studentYear);
	}

	// 독서록 맞춤법 검사
	public static String spelling(int studentYear, String content) {
		return """
			아래 글은 초등학교 %d 학년이 작생한 독서록입니다.
			내용: [%s]
			문장에서 발견되는 맞춤법·띄어쓰기·어휘 사용 오류를 해당 학년 수준에 맞춰 교정해 주세요.
			너무 어렵거나 복잡한 어휘로 교정하지 말고, %d학년 학생이 충분히 이해할 수 있는 수준으로 간단 명료하게 사람어투로 표현해주세요.
			""".formatted(studentYear, content, studentYear);
	}

	// OCR 인식결과 AI 보정
	public static String ocrEnhancer(String content) {
		return """
			        아래 텍스트는 초등학생이 손글씨로 작성한 독서록을 네이버 클로바 OCR 처리한 결과입니다.
			        내용: [%s]
			        초등학생 특성 상 손글씨는 문장은 단어가 끊기거나 일부가 누락, 이상한 단어로 이상하게 나타날 수 있습니다.
			        앞뒤 문맥과 실제 발음을 고려하고, 한국인이 실제 문장을 읽으며 이해하는 것처럼 언어 감각으로 해당 단어들의 의도를 유추하여 자연스러운 문장으로 복원해 주세요.
			        단, 이미지를 텍스트로 추출해주는 서비스이기 때문에 문장의 종결, 반말, 존대, 틀린 맞춤법 그대로 유지하세요.
			""".formatted(content);
	}
}
