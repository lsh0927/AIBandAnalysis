package org.example.band.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.example.band.entity.AudioFile;
import org.example.band.enums.AudioFileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AudioFileRepositoryTest {

	@Autowired
	private AudioFileRepository audioFileRepository;

	@DisplayName("AudioFile 저장 및 조회")
	@Test
	void saveAndFindAudioFile() {
		// given
		AudioFile audioFile = AudioFile.builder()
			.originalFileName("test.mp3")
			.fileUrl("http://example.com/test.mp3")
			.fileSize(1024L)
			.duration(180)
			.type(AudioFileType.REFERENCE)
			.build();

		// when: 저장
		AudioFile savedFile = audioFileRepository.save(audioFile);

		// then: 조회 후 검증
		Optional<AudioFile> found = audioFileRepository.findById(savedFile.getId());
		assertThat(found).isPresent();
		assertThat(found.get().getOriginalFileName()).isEqualTo("test.mp3");
	}
}
