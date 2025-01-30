package org.example.band.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.band.entity.Project;
import org.example.band.entity.User;
import org.example.band.enums.ProjectStatus;
import org.example.band.enums.Provider;
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

	@DisplayName("새로운 프로젝트를 저장한다")
	@Test
	void saveNewProject(){
		//given
		User user= User.builder()
			.email("test@test.com")
			.name("test user1")
			.provider(Provider.KAKAO)
			.providerId("12345")
			.profileImage("http://example.com/profile.jpg")
			.build();
		userRepository.save(user);

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
}