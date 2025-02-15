package com.ssafy.ourdoc.domain.award.dto.teacher;

import java.util.List;

public record AwardTeacherResponse(
	List<AwardTeacherDto> studentAwards
) {
}
