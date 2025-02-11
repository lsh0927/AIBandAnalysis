package org.example.band.repository;

import org.example.band.entity.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioFileRepository extends JpaRepository<AudioFile,Long> {
}
