package org.example.band.service;


import org.springframework.web.multipart.MultipartFile;

public class DummyFileStorageService implements FileStorageService {

	@Override
	public String uploadFile(MultipartFile file) {
		// 실제 S3 연동 전, 더미 URL 반환
		return "http://dummy-storage.com/" + file.getOriginalFilename();
	}

	@Override
	public void deleteFile(String fileUrl) {
		// 더미 구현에서는 아무런 동작 없이 처리
	}
}
