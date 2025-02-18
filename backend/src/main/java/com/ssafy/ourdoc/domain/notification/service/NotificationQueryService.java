package com.ssafy.ourdoc.domain.notification.service;

import static com.ssafy.ourdoc.global.common.enums.NotificationStatus.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.notification.dto.NotificationDetailDto;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDetailDtoConvert;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDto;
import com.ssafy.ourdoc.domain.notification.dto.NotificationDtoConvert;
import com.ssafy.ourdoc.domain.notification.dto.request.NotificationConditionRequest;
import com.ssafy.ourdoc.domain.notification.dto.response.NotificationDetailResponse;
import com.ssafy.ourdoc.domain.notification.dto.response.NotificationListResponse;
import com.ssafy.ourdoc.domain.notification.repository.NotificationQueryRepository;
import com.ssafy.ourdoc.domain.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationQueryService {

	private final NotificationQueryRepository notificationQueryRepository;

	public NotificationListResponse getUnreadNotifications(User user, NotificationConditionRequest request,
		Pageable pageable) {
		Page<NotificationDto> notificationDtoList = notificationQueryRepository.
			findAllConditionByUserId(user.getId(), request, pageable);

		List<NotificationDtoConvert> list = notificationDtoList.stream()
			.map(dto -> new NotificationDtoConvert(
				dto.notificationId(),
				dto.type(),
				dto.content(),
				dto.createdAt(),
				dto.senderName(),
				dto.readTime() == null ? 안읽음 : 읽음)
			)
			.toList();

		Page<NotificationDtoConvert> converts = new PageImpl<>(list, pageable,
			notificationDtoList.getTotalElements());

		return new NotificationListResponse(converts);
	}

	public NotificationDetailResponse getNotification(User user, Long notificationId) {
		NotificationDetailDto dto = notificationQueryRepository.findByNotificationId(user.getId(),
			notificationId);

		if (dto == null) {
			throw new NoSuchElementException("알림이 존재하지 않거나 일치하지 않은 사용자입니다.");
		}

		NotificationDetailDtoConvert build = NotificationDetailDtoConvert.builder()
			.notificationId(dto.notificationId())
			.type(dto.type())
			.senderName(dto.senderName())
			.content(dto.content())
			.createdAt(dto.createdAt())
			.status(dto.readTime() == null ? 안읽음 : 읽음)
			.build();

		return new NotificationDetailResponse(build);
	}
}
