package org.example.band.dto;

import org.example.band.entity.AudioFile;

public record ProjectUpdateRequest(
	ProjectUpdateType updateType,
	String title,            // BASIC_INFO 업데이트시
	String genre,           // BASIC_INFO 업데이트시
	AudioFile audioFile    // FILE 업데이트시
) {}