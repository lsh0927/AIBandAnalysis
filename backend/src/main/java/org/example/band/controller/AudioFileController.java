package org.example.band.controller;

import org.example.band.entity.AudioFile;
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

	// 오디오 파일 업로드 (생성)
	@PostMapping("/upload")
	public ResponseEntity<AudioFile> uploadAudioFile(@RequestParam("file") MultipartFile file) {
		AudioFile savedFile = audioFileService.uploadAudioFile(file);
		return new ResponseEntity<>(savedFile, HttpStatus.CREATED);
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
