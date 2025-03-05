package org.example.band.dto;

import org.example.band.entity.AudioFile;
import org.example.band.enums.AudioFileType;
import org.example.band.enums.FileStatus;

public record AudioFileResponse(
	Long id,
	String originalFileName,
	String fileUrl,
	Long fileSize,
	Integer duration,
	AudioFileType type,
	FileStatus status
) {
	public static AudioFileResponse from(AudioFile audioFile) {
		return new AudioFileResponse(
			audioFile.getId(),
			audioFile.getOriginalFileName(),
			audioFile.getFileUrl(),
			audioFile.getFileSize(),
			audioFile.getDuration(),
			audioFile.getType(),
			audioFile.getStatus()
		);
	}
}
