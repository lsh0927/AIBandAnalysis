package org.example.band.service;

import org.example.band.entity.AudioFile;
import org.example.band.enums.AudioFileType;
import org.example.band.repository.AudioFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AudioFileService {

	private final FileStorageService fileStorageService;
	private final AudioFileRepository audioFileRepository;


	public AudioFile uploadAudioFile(MultipartFile file, String fileType) {
		AudioFileType type;
		if ("reference".equalsIgnoreCase(fileType)) {
			type = AudioFileType.REFERENCE;
		} else if ("performance".equalsIgnoreCase(fileType)) {
			type = AudioFileType.PERFORMANCE;
		} else {
			throw new IllegalArgumentException("Invalid fileType: " + fileType);
		}

		AudioFile audioFile = AudioFile.builder()
			.originalFileName(file.getOriginalFilename())
			.fileUrl("") // 업로드 후 URL로 업데이트 예정
			.fileSize(file.getSize())
			.duration(0) // 지속 시간은 별도 계산하거나 클라이언트가 전달하도록 할 수 있음
			.type(AudioFileType.REFERENCE)
			.type(type)
			.build();

		// S3FileStorageService를 통해 파일 업로드 후 URL 획득
		String fileUrl = fileStorageService.uploadFile(file);

		// 엔티티에 URL 업데이트
		audioFile.updateFileUrl(fileUrl);

		// DB에 저장 후 반환
		return audioFileRepository.save(audioFile);
	}

	public AudioFile getAudioFile(Long id) {
		return audioFileRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Audio file not found"));
	}

	public void deleteAudioFile(Long id) {
		AudioFile audioFile = getAudioFile(id);
		fileStorageService.deleteFile(audioFile.getFileUrl());
		audioFileRepository.delete(audioFile);
	}
}
