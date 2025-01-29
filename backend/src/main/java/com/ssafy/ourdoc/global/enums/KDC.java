package com.ssafy.ourdoc.global.enums;

public enum KDC {
	총류("0"),
	철학("1"),
	종교("2"),
	사회과학("3"),
	자연과학("4"),
	기술과학("5"),
	예술("6"),
	언어("7"),
	문학("8"),
	역사("9");

	private final String code;

	KDC(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static String fromCode(String code) {
		for (KDC kdc : KDC.values()) {
			if (kdc.getCode().equals(code)) {
				return kdc.name();
			}
		}
		return "미분류";
	}
}
