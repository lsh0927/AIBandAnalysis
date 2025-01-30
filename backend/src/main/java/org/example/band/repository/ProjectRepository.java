package org.example.band.repository;

import java.util.List;

import org.example.band.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
	List<Project> findAllByUserId(Long userId);
}
