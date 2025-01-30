package org.example.band.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.example.band.entity.Project;
import org.example.band.entity.User;
import org.example.band.enums.ProjectStatus;
import org.example.band.enums.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("프로젝트 레포지토리 테스트")
@DataJpaTest
class ProjectRepositoryTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	private User user;
	private Project project;

	@BeforeEach
	void setUp() {
		user = User.builder()
			.email("test@test.com")
			.name("test user1")
			.provider(Provider.KAKAO)
			.providerId("12345")
			.profileImage("http://example.com/profile.jpg")
			.build();
		userRepository.save(user);

		project = Project.builder()
			.title("title")
			.genre("rock")
			.user(user)
			.build();
		projectRepository.save(project);
	}

	@DisplayName("새로운 프로젝트를 저장한다")
	@Test
	void saveNewProject(){
		Project savedProject= Project.builder()
			.title("title")
			.genre("rock")
			.user(user)
			.build();
		//when
		projectRepository.save(savedProject);

		//then
		assertThat(savedProject.getUser().getId()).isEqualTo(user.getId());
		assertThat(savedProject.getGenre()).isEqualTo("rock");
		assertThat(savedProject.getTitle()).isEqualTo("title");
		assertThat(savedProject.getStatus()).isEqualTo(ProjectStatus.CREATED);
		assertThat(savedProject.getId()).isNotNull();
	}

	@DisplayName("프로젝트를 조회한다")
	@Test
	void findById(){
		//given은 이미 처리되어 있음
		//when
		Project foundProject = projectRepository.findById(project.getId()).orElseThrow(
			() -> new IllegalArgumentException("프로젝트가 없습니다")
		);

		assertThat(foundProject.getId()).isEqualTo(project.getId());
		assertThat(foundProject.getTitle()).isEqualTo("title");
		assertThat(foundProject.getGenre()).isEqualTo("rock");
	}

	@DisplayName("유저가 만든 모든 프로젝트를 조회한다")
	@Test
	void findAllProjectsById(){
		//given: 같은 유저가 프로젝트를 하나 더 만듦
		Project project2= Project.builder()
			.user(user)
			.genre("jazz")
			.title("title2")
			.build();
		projectRepository.save(project2);
		//when
		List<Project> projectList = projectRepository.findAllByUserId(user.getId());

		//then
		//모든 리스트를 찾아야함
		assertThat(projectList).hasSize(2);
		assertThat(projectList).extracting("title")
			.containsExactlyInAnyOrder("title","title2");

	}

	@DisplayName("프로젝트를 삭제한다")
	@Test
	void deleteProject(){
		//when
		projectRepository.delete(project);

		//then
		assertThat(projectRepository.findById(project.getId())).isEmpty();
	}

	@DisplayName("프로젝트를 업데이트한다")
	@Test
	void updateProject(){
		//given
		project.updateGenre("classic");
		project.updateTitle("newTitle");
		projectRepository.save(project);
		//when
		Project updatedProject= projectRepository.findById(user.getId()).orElseThrow(
			()-> new IllegalArgumentException("프로젝트가 없습니다")
		);

		assertThat(updatedProject.getTitle()).isEqualTo("newTitle");
		assertThat(updatedProject.getGenre()).isEqualTo("classic");
	}
}