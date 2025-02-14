package org.example.band.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.example.band.entity.AudioFile;
import org.example.band.enums.AudioFileType;
import org.example.band.repository.AudioFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class AudioFileServiceTest {

	@Mock
	private FileStorageService fileStorageService;

	@Mock
	private AudioFileRepository audioFileRepository;

	@InjectMocks
	private AudioFileService audioFileService;

	private AudioFile audioFile;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		audioFile = AudioFile.builder()
			.originalFileName("test.mp3")
			.fileUrl(null) // 업로드 후 채워질 예정
			.fileSize(1024L)
			.duration(180)
			.type(AudioFileType.REFERENCE)
			.build();
	}

	@DisplayName("오디오 파일 업로드 테스트")
	@Test
	void uploadAudioFile_shouldSetFileUrl() {

		// given: 테스트용 MockMultipartFile 생성
		MockMultipartFile file = new MockMultipartFile("file", "test.mp3", "audio/mpeg", "dummy content".getBytes());
		String dummyUrl = "http://dummy-storage.com/test.mp3";
		when(fileStorageService.uploadFile(any(MultipartFile.class))).thenReturn(dummyUrl);
		// audioFileRepository.save()가 호출되면 audioFile을 그대로 반환하도록 stubbing
		when(audioFileRepository.save(any(AudioFile.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// when: 오디오 파일 업로드 호출
		AudioFile result = audioFileService.uploadAudioFile(file);

		// then: 반환된 오디오 파일의 fileUrl이 더미 URL로 세팅되었는지 검증
		assertThat(result.getFileUrl()).isEqualTo(dummyUrl);
	}

	@DisplayName("오디오 파일 조회 테스트")
	@Test
	void getAudioFile_shouldThrowException() {
		// given: audioFileRepository.findById()가 빈 Optional 반환
		when(audioFileRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

		// when & then: 조회 시 IllegalArgumentException 발생 검증
		assertThatThrownBy(() -> audioFileService.getAudioFile(1L))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Audio file not found");
	}

	@DisplayName("오디오 파일 삭제 테스트")
	@Test
	void deleteAudioFile_shouldThrowException() {
		// given: audioFileRepository.findById()가 빈 Optional 반환하여 getAudioFile()가 예외를 던지도록 stubbing
		when(audioFileRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

		// when & then: 삭제 시 IllegalArgumentException 발생 검증
		assertThatThrownBy(() -> audioFileService.deleteAudioFile(1L))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Audio file not found");
	}
}
