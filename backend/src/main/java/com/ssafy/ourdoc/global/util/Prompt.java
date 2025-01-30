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
			     다음 글은 초등학교 학생이 손글씨로 작성한 독서록을 OCR로 변환한 결과입니다.
			     내용: [%s]
`			     초등학생인 점을 생각해 문장의 단어, 맞춤법을 수정하지 않고 있는 그대로 보여주세요.
			     손글씨 특성상 일부 단어가 끊기거나 빠져 있을 수 있습니다. 어색한 단어, 말이 안되는 단어가 있다면 앞, 뒤 문장을 기반으로, 실제 발음을 했을 때 유사한 단어를 생각해 해당 단어를 바꿔주세요.
			     예) 영히 -> 분명히, 왕전 -> 완전. 
			""".formatted(content);
	}
}
