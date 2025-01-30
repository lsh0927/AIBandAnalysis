package org.example.band.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@ExtendWith(MockitoExtension.class)
class  JwtTokenProviderTest{

	private JwtTokenProvider jwtTokenProvider;

	@BeforeEach
	void setUp(){
		jwtTokenProvider = new JwtTokenProvider();
		//Jwt 설정
		String jwtSecret= Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());

		//private 변수나 메서드 테스트
		ReflectionTestUtils.setField(jwtTokenProvider,"jwtSecret",jwtSecret);
		ReflectionTestUtils.setField(jwtTokenProvider,"jwtExpirationMs",3600000L);
		jwtTokenProvider.init();
	}

	@Test
	void generateToken_ValidAuthentication_ShouldReturnToken(){
		//given

		//mock으로 인증 객체와 Principal 생성
		Authentication authentication= mock(Authentication.class);
		UserPrincipal userPrincipal= mock(UserPrincipal.class);
		when(authentication.getPrincipal()).thenReturn(userPrincipal);
		when(userPrincipal.getId()).thenReturn(1L);

		//when

		//인증 정보를 가지고 토큰을 생성
		String token= jwtTokenProvider.generateToken(authentication);


		//then
		assertNotNull(token);
		assertTrue(jwtTokenProvider.validateToken(token));
		assertEquals(1L, jwtTokenProvider.getUserIdFromJWT(token));
	}
}