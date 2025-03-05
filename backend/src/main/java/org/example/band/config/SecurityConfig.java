package org.example.band.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final OAuth2UserService oAuth2UserService;
	private final JwtTokenProvider tokenProvider;

	@Value("${app.auth.token-secret}")  // 기존 시크릿 키 사용
	private String jwtSecret;

	@Value("${app.oauth2.authorized-redirect-uris}")
	private String authorizedRedirectUri;

	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String kakaoRedirectUri;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
<<<<<<< HEAD
					"/api/auth/**",
					"/oauth2/**",
					"/login/oauth2/**",
					"/auth/**",
					"/oauth/**",
=======
					// "/api/**",
					// "/api/auth/**",
					// "/oauth2/**",
					// "/login/oauth2/**",
					// "/auth/**",
					// "/oauth/**",
>>>>>>> 61ded68 (again)
					"/oauth2/redirect/**",  // 이 경로 추가
					"/error"             // error 경로도 추가
				).permitAll()
				.anyRequest().authenticated()
			)
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfo -> userInfo
					.userService(oAuth2UserService)
				)
				.successHandler((request, response, authentication) -> {
					log.debug("OAuth2 login success, generating token");
					String token = tokenProvider.generateToken(authentication);
					String redirectUrl = authorizedRedirectUri + "?token=" + token;
					log.debug("Redirecting to: {}", redirectUrl);
					response.sendRedirect(redirectUrl);
				})
			)
			.exceptionHandling(exception ->
			exception.authenticationEntryPoint((request, response, authException) -> {
				response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
			})
		);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList(
			"http://localhost:5173",
			"http://host.docker.internal:5173",
			"https://kauth.kakao.com",    // 카카오 인증 서버
			"https://accounts.kakao.com",   // 카카오 계정 서버
			"http://band-analysis-frontend:5173",
			"http://band-analysis-frontend:8080"
		));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.setExposedHeaders(Arrays.asList("Authorization"));
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}

