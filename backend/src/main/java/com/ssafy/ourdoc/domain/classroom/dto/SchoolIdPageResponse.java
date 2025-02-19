package com.ssafy.ourdoc.domain.classroom.dto;

import org.springframework.data.domain.Page;

public record SchoolIdPageResponse(Page<SchoolIdResponse> school) {
}
