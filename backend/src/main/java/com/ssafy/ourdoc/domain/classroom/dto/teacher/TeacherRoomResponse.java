package com.ssafy.ourdoc.domain.classroom.dto.teacher;

import java.util.List;
import java.util.Map;

public record TeacherRoomResponse(
	Map<String, List<TeachersRoomDto>> teachersRoom
) {
}
