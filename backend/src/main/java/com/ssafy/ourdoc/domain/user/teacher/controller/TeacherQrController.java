package com.ssafy.ourdoc.domain.user.teacher.controller;

import com.ssafy.ourdoc.domain.user.teacher.service.TeacherQrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherQrController {

    private final TeacherQrService teacherQrService;

    // 1. QR 생성
    @GetMapping(value = "/{teacherId}/code", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateTeacherInviteCode(@PathVariable Long teacherId) {
        byte[] qrImage = teacherQrService.generateTeacherClassQr(teacherId);
        return ResponseEntity.ok(qrImage);
    }
}
