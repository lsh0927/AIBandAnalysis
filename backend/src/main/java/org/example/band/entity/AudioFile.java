package org.example.band.entity;

import java.util.HashMap;
import java.util.Map;

import org.example.band.enums.AudioFileType;
import org.example.band.enums.FileStatus;
import org.example.band.enums.InstrumentType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "audio_files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AudioFile extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String originalFileName;

	@Column(nullable = false)
	private String fileUrl;

	private Long fileSize;

	private Integer duration;

	@Enumerated(EnumType.STRING)
	private AudioFileType type;  // REFERENCE 또는 PERFORMANCE

	@Enumerated(EnumType.STRING)
	private FileStatus status;

	// 분석된 악기 트랙 정보를 JSON으로 저장
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private Map<InstrumentType, Map<String, Object>> trackInfo = new HashMap<>();

	@Builder
	public AudioFile(String originalFileName, String fileUrl, Long fileSize,
		Integer duration, AudioFileType type) {
		this.originalFileName = originalFileName;
		this.fileUrl = fileUrl;
		this.fileSize = fileSize;
		this.duration = duration;
		this.type = type;
		this.status = FileStatus.UPLOADED;
	}
}