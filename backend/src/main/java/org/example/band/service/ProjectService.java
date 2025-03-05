package org.example.band.service;

import static org.example.band.dto.ProjectUpdateType.*;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 61ded68 (again)
import org.example.band.dto.ProjectCreateRequest;
import org.example.band.dto.ProjectCreateResponse;
import org.example.band.dto.ProjectResponse;
import org.example.band.dto.ProjectUpdateRequest;
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

	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;

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

	public ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

		// 업데이트 타입별 처리
		switch (request.updateType()) {
			case BASIC_INFO:
				if (request.title() != null) project.updateTitle(request.title());
				if (request.genre() != null) project.updateGenre(request.genre());
				break;
			case REFERENCE_FILE:
				if (request.audioFile() == null) {
					throw new IllegalArgumentException("레퍼런스 파일이 제공되지 않았습니다.");
				}
				project.setReferenceFile(request.audioFile());
				break;
			case PERFORMANCE_FILE:
				if (request.audioFile() == null) {
					throw new IllegalArgumentException("연주 파일이 제공되지 않았습니다.");
				}
				project.setPerformanceFile(request.audioFile());
				break;
			default:
				throw new UnsupportedOperationException("지원하지 않는 업데이트 타입입니다.");
		}

		projectRepository.save(project);
		return ProjectResponse.from(project);
	}

	public void deleteProject(Long projectId) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));
		projectRepository.delete(project);
	}
<<<<<<< HEAD
=======

	public List<ProjectResponse> findAllProjects() {
		List<Project> projects = projectRepository.findAll();
		return projects.stream()
			.map(ProjectResponse::from)
			.toList(); // Java 16+에서는 .collect(Collectors.toList()) 대신 .toList() 사용 가능
	}

>>>>>>> 61ded68 (again)
}
