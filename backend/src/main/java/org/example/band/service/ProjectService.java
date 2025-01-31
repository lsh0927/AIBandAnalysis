package org.example.band.service;

import org.example.band.dto.ProjectCreateRequest;
import org.example.band.dto.ProjectCreateResponse;
import org.example.band.dto.ProjectResponse;
import org.example.band.entity.Project;
import org.example.band.entity.User;
import org.example.band.repository.ProjectRepository;
import org.example.band.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

	private ProjectRepository projectRepository;
	private UserRepository userRepository;

	public ProjectCreateResponse createProject(Long userId, ProjectCreateRequest request) {
		//검증
		User user= userRepository.findById(userId).orElseThrow(
			()-> new IllegalArgumentException("유저가 없습니다.")
		);

		Project project = Project.builder()
			.title(request.title())
			.genre(request.genre())
			.user(user)
			.build();

		Project savedProject = projectRepository.save(project);

		//from 메서드로 응답 구성 후 전달
		return ProjectCreateResponse.from(savedProject);
	}

	public ProjectResponse findProject(Long id) {
		Project project= projectRepository.findById(id).orElseThrow(
			()-> new IllegalArgumentException("프로젝트가 없습니다")
		);

		return ProjectResponse.from(project);
	}
}
