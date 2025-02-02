package com.ssafy.ourdoc.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ourdoc.domain.notification.entity.NotificationRecipient;

public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {
}
