package org.example.band.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.example.band.entity.User;
import org.example.band.enums.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//JPA 컴포넌트 테스트
@ActiveProfiles("test")
//yml의 Profile에서 test를 찾아 실행
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@DisplayName("email과 Provider를 입력받아 유저를 리턴")
	@Test
	void findFirstByEmailAndProvider_ShouldReturnUser(){
		// given
		String email = "test@test.com";
		Provider provider = Provider.KAKAO;

		User user= User.builder()
			.email(email)
			.name("test user1")
			.provider(provider)
			.providerId("12345")
			.profileImage("http://example.com/profile.jpg")
			.build();

		userRepository.save(user);

		//when
		Optional<User> found = userRepository.findFirstByEmailAndProvider(email, provider);

		//then
		assertThat((found).isPresent());
		assertThat((found.get().getEmail())).isEqualTo(email);
		assertThat((found.get().getProvider())).isEqualTo(provider);
	}

	@DisplayName("email과 Provider에 해당하는 유저가 없다면 null리턴")
	@Test
	void findFirstByEmailAndProvider_WhenUserNotFound(){
		// given
		String email = "NotExist@test.com";
		Provider provider = Provider.KAKAO;

		//when
		Optional<User> found = userRepository.findFirstByEmailAndProvider(email, provider);

		//then
		assertThat((found).isEmpty());
	}
}