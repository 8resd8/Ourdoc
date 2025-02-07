package com.ssafy.ourdoc.domain.debate.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.ourdoc.domain.debate.dto.CreateRoomRequest;
import com.ssafy.ourdoc.domain.debate.dto.JoinRoomRequest;
import com.ssafy.ourdoc.domain.debate.entity.Room;
import com.ssafy.ourdoc.domain.debate.entity.RoomOnline;
import com.ssafy.ourdoc.domain.debate.repository.RoomOnlineRepository;
import com.ssafy.ourdoc.domain.debate.repository.RoomRepository;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.common.enums.Active;
import com.ssafy.ourdoc.global.common.enums.Gender;
import com.ssafy.ourdoc.global.common.enums.UserType;
import com.ssafy.ourdoc.global.exception.ForbiddenException;
import com.ssafy.ourdoc.global.integration.openvidu.service.OpenviduService;

@ExtendWith(MockitoExtension.class)
class DebateServiceTest {

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private RoomOnlineRepository roomOnlineRepository;

	@Mock
	private OpenviduService openviduService;

	@InjectMocks
	private DebateService debateService;

	private User teacher;
	private User student;
	private CreateRoomRequest createRoomRequest;
	private JoinRoomRequest joinRoomRequest;
	private Room room;

	@BeforeEach
	void setUp() {
		String hashedPassword = BCrypt.hashpw("password123", BCrypt.gensalt());

		teacher = User.builder()
			.userType(UserType.교사)
			.name("테스트교사")
			.loginId("teacher123")
			.password(hashedPassword)
			.birth(Date.valueOf("1980-01-01"))
			.gender(Gender.남)
			.active(Active.활성)
			.build();

		student = User.builder()
			.userType(UserType.학생)
			.name("테스트학생")
			.loginId("student123")
			.password(hashedPassword)
			.birth(Date.valueOf("2010-12-31"))
			.gender(Gender.여)
			.active(Active.비활성)
			.build();

		createRoomRequest = new CreateRoomRequest("테스트방", "password1234", 5);

		joinRoomRequest = new JoinRoomRequest("password1234");

		room = Room.builder()
			.sessionId("sessionId123")
			.user(teacher)
			.title("테스트방")
			.password("password1234")
			.maxPeople(5)
			.build();
	}

	@Test
	@DisplayName("독서토론방 생성 성공")
	void createDebateRoom_success() {
		when(openviduService.createSession()).thenReturn("sessionId123");
		when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

		debateService.createDebateRoom(teacher, createRoomRequest);

		verify(roomRepository, times(1)).save(any(Room.class));
	}

	@Test
	@DisplayName("학생 독서토론방 생성 권한 없음")
	void createDebateRoom_failure_studentCreate() {
		assertThatThrownBy(() -> debateService.createDebateRoom(student, createRoomRequest))
			.isInstanceOf(ForbiddenException.class)
			.hasMessage("독서토론방 생성 권한이 없습니다.");
	}

	@Test
	@DisplayName("독서토론방 참가 성공")
	void joinDebateRoom_success() {
		Long roomId = 1L;
		when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
		when(roomOnlineRepository.countCurrentPeople(room.getId())).thenReturn(3L);
		when(openviduService.generateToken(room.getSessionId())).thenReturn("token123");

		String token = debateService.joinDebateRoom(teacher, roomId, joinRoomRequest);

		assertThat(token).isEqualTo("token123");
		verify(roomRepository).findById(roomId);
		verify(openviduService).generateToken(room.getSessionId());
		verify(roomOnlineRepository).save(any(RoomOnline.class));
	}

	@Test
	@DisplayName("존재하지 않는 독서토론방 입장 실패")
	void joinDebateRoom_failure_roomNotFound() {
		Long roomId = 1L;
		when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> debateService.joinDebateRoom(teacher, roomId, joinRoomRequest))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("해당 방은 존재하지 않습니다.");
	}

	@Test
	@DisplayName("종료된 독서토론방 입장 실패")
	void joinDebateRoom_failure_roomEnded() {
		Long roomId = 1L;
		Room endedRoom = Room.builder()
			.sessionId("sessionId123")
			.user(teacher)
			.title("종료된 테스트방")
			.password("password1234")
			.maxPeople(5)
			.build();
		endedRoom = spy(endedRoom);
		when(endedRoom.getEndAt()).thenReturn(LocalDateTime.now());
		when(roomRepository.findById(roomId)).thenReturn(Optional.of(endedRoom));

		assertThatThrownBy(() -> debateService.joinDebateRoom(teacher, roomId, joinRoomRequest))
			.isInstanceOf(ForbiddenException.class)
			.hasMessage("해당 방은 종료되었습니다.");
	}

	@Test
	@DisplayName("최대 인원을 가진 방 입장 실패")
	void joinDebateRoom_failure_maxPeople() {
		Long roomId = 1L;
		when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
		when(roomOnlineRepository.countCurrentPeople(room.getId())).thenReturn(Long.valueOf(room.getMaxPeople()));

		assertThatThrownBy(() -> debateService.joinDebateRoom(student, roomId, joinRoomRequest))
			.isInstanceOf(ForbiddenException.class)
			.hasMessage("방에 빈자리가 없습니다.");
	}

	@Test
	@DisplayName("세션이 생성되지 않은 방 입장 실패")
	void joinDebateRoom_failure_noSessionId() {
		Long roomId = 1L;
		Room noSessionIdRoom = Room.builder()
			.sessionId(null)
			.user(teacher)
			.title("세션이 없는 테스트방")
			.password("password1234")
			.maxPeople(5)
			.build();
		when(roomRepository.findById(roomId)).thenReturn(Optional.of(noSessionIdRoom));

		assertThatThrownBy(() -> debateService.joinDebateRoom(teacher, roomId, joinRoomRequest))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("해당 방은 세션이 존재하지 않습니다.");
	}
}