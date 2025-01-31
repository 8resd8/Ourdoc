package com.ssafy.ourdoc.domain.user.teacher.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ssafy.ourdoc.domain.user.teacher.entity.Teacher;
import com.ssafy.ourdoc.domain.user.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeacherQrService {

    private final TeacherRepository teacherRepository;

    // 1. QR 생성
    public byte[] generateTeacherClassQr(Long teacherId) {
        // 1) 교사 조회
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 교사가 없습니다: " + teacherId));

//        // 2) 소속 반 정보
//        if (teacher.getClassRoom() == null) {
//            throw new IllegalStateException("교사에 연결된 ClassRoom 정보가 없습니다.");
//        }
//
//        Long schoolId = teacher.getClassRoom().getSchoolId();
//        int grade = teacher.getClassRoom().getGrade();
//        int classNumber = teacher.getClassRoom().getClassNumber();

        // 3) QR에 담을 json 데이터
        // 학교, 학년, 반은 추후 jpa 사용하도록 수정 필요(현재는 예시로 넣어본 것)
        String googleFormLink = String.format(
                "https://docs.google.com/forms/d/e/1FAIpQLSfxNCzcxL07Kzo27rCINXu4PHxco7Y4aT8iyi3ys-3PU5j5fg/viewform?usp=pp_url&entry.93879875=%s&entry.631690045=%d&entry.581945071=%d",
                "ㅇㅇ초등학교", // 학교
                1,             // 학년
                2              // 반
        );

        // json 데이터의 한글이 깨지지 않도록 설정
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  // 한글 지원
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 에러 정정 수준(L/M/Q/H)

        try {
            // 4) QR BitMatrix 생성
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(googleFormLink, // 여기 데이터를 넣음
                            BarcodeFormat.QR_CODE,
                            300, // width
                            300, // height
                            hints
                    );

            // 5) BitMatrix -> BufferedImage 변환
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // 6) PNG 형식으로 byte[] 출력
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(qrImage, "png", baos);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("QR 코드 생성 중 오류가 발생했습니다.", e);
        }
    }
}
