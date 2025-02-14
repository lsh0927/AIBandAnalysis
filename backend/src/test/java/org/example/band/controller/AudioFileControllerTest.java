package org.example.band.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.example.band.config.JwtTokenProvider;
import org.example.band.entity.AudioFile;
import org.example.band.enums.AudioFileType;
import org.example.band.repository.UserRepository;
import org.example.band.service.AudioFileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

@WebMvcTest(AudioFileController.class)
@AutoConfigureMockMvc(addFilters = false)
class AudioFileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private AudioFileService audioFileService;

	@DisplayName("오디오 파일 업로드 API 테스트")
	@Test
	void uploadAudioFile() throws Exception {
		// given: 더미 AudioFile 객체 생성
		AudioFile dummyFile = AudioFile.builder()
			.originalFileName("upload.mp3")
			.fileUrl("http://dummy-storage.com/upload.mp3")
			.fileSize(2048L)
			.duration(200)
			.type(AudioFileType.REFERENCE)
			.build();
		when(audioFileService.uploadAudioFile(any(MultipartFile.class))).thenReturn(dummyFile);

		String jsonRequest = "{\"originalFileName\":\"upload.mp3\","
			+ "\"fileUrl\":\"http://dummy-storage.com/upload.mp3\","
			+ "\"fileSize\":2048,"
			+ "\"duration\":200,"
			+ "\"type\":\"REFERENCE\"}";

		// when & then: POST 요청 후 201 응답과 JSON 필드 검증
		mockMvc.perform(multipart("/api/audio/upload")
				.file(new MockMultipartFile("file", "upload.mp3", "audio/mpeg", "dummy content".getBytes())))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.originalFileName").value("upload.mp3"))
			.andExpect(jsonPath("$.fileUrl").value("http://dummy-storage.com/upload.mp3"));

	}

		@DisplayName("오디오 파일 조회 API 테스트")
	@Test
	void getAudioFile() throws Exception {
		// given: 더미 AudioFile 객체 생성
		AudioFile dummyFile = AudioFile.builder()
			.originalFileName("view.mp3")
			.fileUrl("http://dummy-storage.com/view.mp3")
			.fileSize(1024L)
			.duration(180)
			.type(AudioFileType.REFERENCE)
			.build();
		when(audioFileService.getAudioFile(anyLong())).thenReturn(dummyFile);

		// when & then: GET 요청 후 200 응답 및 JSON 검증
		mockMvc.perform(get("/api/audio/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.originalFileName").value("view.mp3"))
			.andExpect(jsonPath("$.fileUrl").value("http://dummy-storage.com/view.mp3"));
	}

	@DisplayName("오디오 파일 삭제 API 테스트")
	@Test
	void deleteAudioFile() throws Exception {
		// given: deleteAudioFile 메서드가 아무 작업도 하지 않음
		doNothing().when(audioFileService).deleteAudioFile(anyLong());

		// when & then: DELETE 요청 후 204(No Content) 응답 검증
		mockMvc.perform(delete("/api/audio/1"))
			.andExpect(status().isNoContent());
	}
}
