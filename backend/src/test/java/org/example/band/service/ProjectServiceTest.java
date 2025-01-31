package org.example.band.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.example.band.dto.ProjectCreateRequest;
import org.example.band.dto.ProjectCreateResponse;
import org.example.band.dto.ProjectResponse;
import org.example.band.entity.Project;
import org.example.band.entity.User;
import org.example.band.enums.Provider;
import org.example.band.repository.ProjectRepository;
import org.example.band.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

	@Mock
	private ProjectRepository projectRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private ProjectService projectService;

	private User user;
	private Project project;
	@BeforeEach
	void setUp() {
		user = User.builder()
			.email("test1234@test.com")
			.name("test user1")
			.provider(Provider.KAKAO)
			.providerId("12345")
			.profileImage("http://example.com/profile.jpg")
			.build();

		project= Project.builder()
			.title("title")
			.genre("rock")
			.user(user)
			.build();

	}

	@DisplayName("새로운 프로젝트를 생성한다")
	@Test
	void createProject() {
		//given
		ProjectCreateRequest request = new ProjectCreateRequest("title", "genre");

		Project project = Project.builder()
			.title(request.title())
			.genre(request.genre())
			.user(user)
			.build();

		//mock 동작 정의
		//findById에서 user.getId를 쓰지 않는 이유: 실제론 id가 생기지 않고 그냥 mock으로 하기 때문
		//user.getId()는 null을 반환(DB에 저장하지 않았기 때문에 ID가 없음). 하지만 실제 서비스에서는 1L을 인자로 전달
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(projectRepository.save(any(Project.class))).thenReturn(project);

		//when
		ProjectCreateResponse response = projectService.createProject(1L, request);

		//then
		assertThat(response.title()).isEqualTo("title");
		assertThat(response.genre()).isEqualTo("genre");
		verify(projectRepository).save(any(Project.class));
	}

	@Test
	@DisplayName("존재하지 않는 유저로 프로젝트 생성시 예외가 발생한다")
	void createProject_UserNotFound() {
		// given: userRepository가 empty Optional을 반환하도록 설정 (유저가 없는 상황 가정)
		ProjectCreateRequest request = new ProjectCreateRequest("title", "genre");
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> projectService.createProject(1L, request))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("유저가 없습니다.");
	}

	@DisplayName("프로젝트 ID로 프로젝트를 조회한다")
	@Test
	void findProject(){
		//given: projectRepository가 project를 반환하도록 설정
		when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

		//when
		ProjectResponse response= projectService.findProject(1L);
		// then
		assertThat(response.title()).isEqualTo(project.getTitle());
		assertThat(response.genre()).isEqualTo(project.getGenre());
	}
}