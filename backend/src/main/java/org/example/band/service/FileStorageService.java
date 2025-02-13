package org.example.band.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	// 파일을 업로드하고 저장된 파일의 URL을 반환
	String uploadFile(MultipartFile file);

	 // 주어진 URL에 해당하는 파일을 삭제
	void deleteFile(String fileUrl);
}