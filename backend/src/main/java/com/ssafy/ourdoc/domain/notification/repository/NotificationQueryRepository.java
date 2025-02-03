package com.ssafy.ourdoc.domain.notification.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;

public interface NotificationQueryRepository {

	List<NotificationDto> findAllByUserId(Long userId, Pageable pageable);

}
