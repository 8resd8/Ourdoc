package com.ssafy.ourdoc.domain.user.teacher.dto;

import org.springframework.data.domain.Page;

public record StudentListResponse(Page<StudentProfileDto> studentProfiles) {
}
