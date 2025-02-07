package com.ssafy.ourdoc.domain.user.student.dto;

import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.entity.School;

public record ValidatedEntities(
	String encodedPassword,
	School school,
	ClassRoom classRoom
) {}