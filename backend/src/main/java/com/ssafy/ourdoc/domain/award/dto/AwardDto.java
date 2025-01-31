package com.ssafy.ourdoc.domain.award.dto;

import java.time.LocalDateTime;

public record AwardDto(Long id, String imagePath, String title, LocalDateTime createdAt) {
}
