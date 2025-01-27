package com.ssafy.ourdoc.global.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ourdoc.global.exception.FileUploadException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3StorageService {

	private static final Set<String> ALLOWED_EXTENSIONS = Stream.of("jpg", "jpeg", "png", "webp")
		.collect(Collectors.toSet());

	private static final Set<String> ALLOWED_MIME_TYPES = Set.of("image/jpeg", "image/png", "image/webp");

	private final S3Client s3Client;

	@Value("${aws.s3.bucket-name}")
	private String bucketName;

	@Value("${aws.s3.upload.access-url}") // AWS S3 접근 URL
	private String uploadAccessUrl;

	// 단일 파일 업로드
	public String uploadFile(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return null;
		}

		validateFile(file);

		String fileName = "ocr/" + UUID.randomUUID() + "_" + file.getOriginalFilename(); // 일단 ocr 1개한정

		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(bucketName)
			.key(fileName)
			.acl("public-read") // 퍼블릭 읽기 권한
			.build();

		try {
			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
		} catch (IOException e) {
			throw new FileUploadException("파일 업로드 실패: " + file.getOriginalFilename(), e);
		}

		return uploadAccessUrl + fileName; // 클라이언트가 접속할 수 있는 URL
	}

	// 다중 파일 업로드
	public List<String> uploadFiles(MultipartFile[] files) {
		if (files == null || files.length == 0) {
			return List.of();
		}

		List<String> fileUrls = new ArrayList<>();
		for (MultipartFile file : files) {
			String fileUrl = uploadFile(file);
			fileUrls.add(fileUrl);
		}

		return fileUrls;
	}

	// 파일 유효성 검사
	private void validateFile(MultipartFile file) {
		int extension = file.getOriginalFilename().lastIndexOf('.');
		if (extension == -1) {
			throw new FileUploadException("파일 확장자가 없습니다.");
		}

		String mimeType = file.getContentType();
		if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType.toLowerCase())) {
			throw new FileUploadException("ContentType 확인하세요");
		}

		String fileExtension = file.getOriginalFilename().substring(extension + 1).toLowerCase(); // 확장자만 확인
		if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
			throw new FileUploadException("허용되지 않는 파일 형식입니다. (허용되는 형식: " + ALLOWED_EXTENSIONS + ")");
		}
	}
}
