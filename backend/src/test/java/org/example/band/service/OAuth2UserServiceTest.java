package org.example.band.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.example.band.entity.User;
import org.example.band.enums.Provider;
import org.example.band.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class OAuth2UserServiceTest {

	@Mock
	private UserRepository userRepository;

	private OAuth2UserService oAuth2UserService;

	@BeforeEach
	void setUp() {
		oAuth2UserService = new OAuth2UserService(userRepository);
	}

	@Test
	void getAttributeValue_ShouldReturnNestedAttribute() {
		// Given
		Map<String, Object> properties = new HashMap<>();
		properties.put("nickname", "TestUser");

		Map<String, Object> attributes = new HashMap<>();
		attributes.put("properties", properties);

		OAuth2User oauth2User = mock(OAuth2User.class);
		when(oauth2User.getAttributes()).thenReturn(attributes);

		// When

		// private 메서드에 접근하기 위함
		String result = (String) ReflectionTestUtils.invokeMethod(oAuth2UserService, "getAttributeValue", oauth2User, "properties.nickname");

		// Then
		assertEquals("TestUser", result);
	}

	@Test
	void createUser_ShouldSaveNewUser() {
		// Given
		String email = "test@example.com";
		String name = "TestUser";
		String profileImage = "http://example.com/image.jpg";
		Provider provider = Provider.KAKAO;
		String providerId = "12345";

		User expectedUser = User.builder()
			.email(email)
			.name(name)
			.profileImage(profileImage)
			.provider(provider)
			.providerId(providerId)
			.build();

		when(userRepository.save(any(User.class))).thenReturn(expectedUser);

		// When
		User result = (User) ReflectionTestUtils.invokeMethod(oAuth2UserService, "createUser", email, name, profileImage, provider, providerId);

		// Then
		verify(userRepository).save(any(User.class));
		assertEquals(email, result.getEmail());
		assertEquals(name, result.getName());
		assertEquals(profileImage, result.getProfileImage());
		assertEquals(provider, result.getProvider());
		assertEquals(providerId, result.getProviderId());
	}
}