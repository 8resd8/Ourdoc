package com.ssafy.ourdoc.domain.notification.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.ourdoc.data.entity.UserSample;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.UserType;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class NotificationServiceTest {

	@Autowired
	private NotificationService notificationService;
	private User teacher;

	@Autowired
	private EntityManager em;

	@BeforeEach
	void setUp() {
		teacher = UserSample.user(UserType.교사);
		em.persist(teacher);
	}

	@Test
	void 구독_등록성공() {
		SseEmitter emitter = notificationService.subscribe(teacher);

		assertThat(emitter).isNotNull();

		ConcurrentHashMap<Long, List<SseEmitter>> emitters = notificationService.getEmitters();
		assertThat(emitters.containsKey(teacher.getId())).isTrue();
		assertThat(emitters.get(teacher.getId())).contains(emitter);
	}
}
