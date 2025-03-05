package org.example.band.controller;

import org.example.band.entity.AudioFile;
import org.example.band.dto.AudioFileResponse;
import org.example.band.entity.AudioFile;
import org.example.band.entity.Project;
import org.example.band.enums.AudioFileType;
import org.example.band.repository.AudioFileRepository;
import org.example.band.repository.ProjectRepository;
import org.example.band.service.AudioFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/audio")
@RequiredArgsConstructor
public class AudioFileController {

	private final AudioFileService audioFileService;
	private final ProjectRepository projectRepository;

	@PostMapping("/upload")
	public ResponseEntity<AudioFileResponse> uploadAudioFile(
		@RequestParam("file") MultipartFile file,
		@RequestParam("type") String fileType,
		@RequestParam("projectId") Long projectId
	) {
		AudioFile savedAudio = audioFileService.uploadAudioFile(file, fileType);

		// 2) 프로젝트와 연결
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new IllegalArgumentException("프로젝트가 없습니다"));

		if ("reference".equalsIgnoreCase(fileType)) {
			project.setReferenceFile(savedAudio);
		} else {
			project.setPerformanceFile(savedAudio);
		}
		projectRepository.save(project);

		// 3) AudioFileResponse 반환
		AudioFileResponse responseDto = AudioFileResponse.from(savedAudio);
		return ResponseEntity.ok(responseDto);
	}


	// 오디오 파일 조회
	@GetMapping("/{id}")
	public ResponseEntity<AudioFile> getAudioFile(@PathVariable Long id) {
		AudioFile audioFile = audioFileService.getAudioFile(id);
		return ResponseEntity.ok(audioFile);
	}

	// 오디오 파일 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAudioFile(@PathVariable Long id) {
		audioFileService.deleteAudioFile(id);
		return ResponseEntity.noContent().build();
	}
}
