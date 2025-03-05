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

	/**
	 * MultipartFile을 받아 AudioFile 엔티티를 생성하고,
	 * FileStorageService를 이용해 S3에 파일을 업로드한 후 반환된 URL을 저장
	 * @param file 업로드할 MultipartFile
	 * @return 저장된 AudioFile 엔티티
	 */
<<<<<<< HEAD
	public AudioFile uploadAudioFile(MultipartFile file) {
=======
	public AudioFile uploadAudioFile(MultipartFile file, String fileType) {
		// fileType에 따라 AudioFileType 결정 (예: "reference" 또는 "performance")
		AudioFileType type;
		if ("reference".equalsIgnoreCase(fileType)) {
			type = AudioFileType.REFERENCE;
		} else if ("performance".equalsIgnoreCase(fileType)) {
			type = AudioFileType.PERFORMANCE;
		} else {
			throw new IllegalArgumentException("Invalid fileType: " + fileType);
		}

>>>>>>> 61ded68 (again)
		// MultipartFile로부터 AudioFile 엔티티 생성 (필요한 메타데이터 추출)
		AudioFile audioFile = AudioFile.builder()
			.originalFileName(file.getOriginalFilename())
			.fileUrl("") // 업로드 후 URL로 업데이트 예정
			.fileSize(file.getSize())
			.duration(0) // 지속 시간은 별도 계산하거나 클라이언트가 전달하도록 할 수 있음
<<<<<<< HEAD
			.type(AudioFileType.REFERENCE) // 필요에 따라 변경
=======
			.type(type)
>>>>>>> 61ded68 (again)
			.build();

		// S3FileStorageService를 통해 파일 업로드 후 URL 획득
		String fileUrl = fileStorageService.uploadFile(file);

		// 엔티티에 URL 업데이트
		audioFile.updateFileUrl(fileUrl);

		// DB에 저장 후 반환
		return audioFileRepository.save(audioFile);
	}

<<<<<<< HEAD
=======

>>>>>>> 61ded68 (again)
	/**
	 * 주어진 id로 AudioFile 메타데이터를 조회
	 * @param id 오디오 파일의 고유 식별자
	 * @return 조회된 AudioFile 엔티티
	 */
	public AudioFile getAudioFile(Long id) {
		return audioFileRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Audio file not found"));
	}

	/**
	 * 오디오 파일 삭제:
	 * 1. DB에서 해당 AudioFile 엔티티를 조회
	 * 2. FileStorageService를 호출해 S3에서 실제 파일 삭제
	 * 3. DB에서 메타데이터 삭제
	 *
	 * @param id 삭제할 오디오 파일의 고유 식별자
	 */
	public void deleteAudioFile(Long id) {
		AudioFile audioFile = getAudioFile(id);
		fileStorageService.deleteFile(audioFile.getFileUrl());
		audioFileRepository.delete(audioFile);
	}
}
