package com.ssafy.ourdoc.user.teacher.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.zxing.WriterException;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import com.ssafy.ourdoc.domain.user.teacher.service.TeacherQrService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TeacherQrServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherQrService teacherQrService;

    private Teacher mockTeacher;

    @BeforeEach
    void setUp() {
        mockTeacher = Teacher.builder()
                .email("teacher@example.com")
                .phone("010-1234-5678")
                .build();
    }

    @Test
    @DisplayName("QR 코드 생성 성공 - 정상적인 교사 ID 제공")
    void generateTeacherClassQr_Success() throws WriterException, IOException {
        // Given: Mock Teacher 객체가 존재하는 경우
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(mockTeacher));

        // When: QR 코드 생성
        byte[] qrBytes = teacherQrService.generateTeacherClassQr(1L);

        // Then: 반환된 바이트 배열이 비어 있지 않아야 함
        assertNotNull(qrBytes);
        assertTrue(qrBytes.length > 0);

        // Verify: findById가 한 번 호출되었는지 확인
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("QR 코드 생성 실패 - 존재하지 않는 교사 ID 제공")
    void generateTeacherClassQr_TeacherNotFound() {
        // Given: Teacher가 존재하지 않는 경우
        when(teacherRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then: 예외 발생 검증
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            teacherQrService.generateTeacherClassQr(99L);
        });

        // Then: 예외 메시지 검증
        assertEquals("해당 ID의 교사가 없습니다: 99", exception.getMessage());

        // Verify: findById가 한 번 호출되었는지 확인
        verify(teacherRepository, times(1)).findById(99L);
    }
}
