package com.ssafy.ourdoc.domain.notification.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.ourdoc.domain.notification.entity.NotificationRecipient;

public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {

	@Query("select n from NotificationRecipient n where n.recipient = :userId and n.notification.id = :notificationId")
	Optional<NotificationRecipient> findByNotificationRecipient(Long userId, Long notificationId);
}
