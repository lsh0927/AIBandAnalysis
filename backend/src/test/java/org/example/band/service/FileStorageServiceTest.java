package org.example.band.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class FileStorageServiceTest {

	// 초기에는 더미 구현을 사용
	private final FileStorageService fileStorageService = new DummyFileStorageService();

	@Test
	void uploadFile_returnsValidUrl() {
		// given: 테스트용 MockMultipartFile 생성
		MockMultipartFile file = new MockMultipartFile(
			"file",
			"test.mp3",
			"audio/mpeg",
			"dummy content".getBytes()
		);

		// when: 파일 업로드
		String url = fileStorageService.uploadFile(file);

		// then: URL이 null이 아니고, 기대하는 형식(http://dummy-storage.com/...)인지 검증
		assertThat(url).isNotNull();
		assertThat(url).startsWith("http://dummy-storage.com/");
	}
}
