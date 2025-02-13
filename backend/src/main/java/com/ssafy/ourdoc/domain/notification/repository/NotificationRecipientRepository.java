package com.ssafy.ourdoc.domain.notification.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.ourdoc.domain.notification.entity.NotificationRecipient;

public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {

	@Query("select n from NotificationRecipient n where n.recipient.id = :userId and n.notification.id = :notificationId")
	Optional<NotificationRecipient> findByNotificationRecipient(@Param("userId") Long userId,
		@Param("notificationId") Long notificationId);
}
