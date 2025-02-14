package org.example.band.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

class S3FileStorageServiceTest {

	private AmazonS3 amazonS3;
	private S3FileStorageService s3FileStorageService;

	private final String bucketName = "test-bucket";

	@BeforeEach
	void setUp() {
		amazonS3 = mock(AmazonS3.class);
		s3FileStorageService = new S3FileStorageService(amazonS3);
		// 주입된 bucketName는 @Value로 설정되므로 ReflectionTestUtils로 설정
		ReflectionTestUtils.setField(s3FileStorageService, "bucketName", bucketName);
	}

	@DisplayName("S3 파일 업로드 테스트")
	@Test
	void uploadFile_shouldReturnUrl() throws MalformedURLException {
		// given: 테스트용 MockMultipartFile 생성
		MockMultipartFile file = new MockMultipartFile("file", "test.mp3", "audio/mpeg", "dummy content".getBytes());

		// when: amazonS3.getUrl()의 반환값 설정
		URL dummyUrl = new URL("http://dummy-storage.com/123_test.mp3");
		when(amazonS3.getUrl(eq(bucketName), anyString())).thenReturn(dummyUrl);

		// amazonS3.putObject()는 PutObjectResult를 반환하므로, doNothing() 대신 thenReturn() 사용
		when(amazonS3.putObject(any(PutObjectRequest.class))).thenReturn(new PutObjectResult());

		// when
		String url = s3FileStorageService.uploadFile(file);

		// then: 반환된 URL이 dummyUrl과 일치하는지 검증
		assertThat(url).isEqualTo("http://dummy-storage.com/123_test.mp3");
	}

	@DisplayName("S3 파일 삭제 테스트")
	@Test
	void deleteFile_shouldCallDeleteObject() {
		// given: 삭제할 파일 URL
		String dummyUrl = "http://dummy-storage.com/123_test.mp3";

		// when
		s3FileStorageService.deleteFile(dummyUrl);

		// then: amazonS3.deleteObject가 bucketName과 파일 이름 "123_test.mp3"로 호출되었는지 검증
		verify(amazonS3).deleteObject(argThat((DeleteObjectRequest req) ->
			req.getBucketName().equals(bucketName) &&
				req.getKey().equals("123_test.mp3")
		));
	}
}
