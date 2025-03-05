package org.example.band.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	@Value("${app.auth.token-secret}")
	private String jwtSecret;

	@Value("${app.auth.token-expiration-msec}")
	private long jwtExpirationMs;

	private Key key;

	@PostConstruct
	public void init() {
		byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	// public String generateToken(Authentication authentication) {
	// 	UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
	//
	// 	Date now = new Date();
	// 	Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
	//
	// 	return Jwts.builder()
	// 		.setSubject(Long.toString(userPrincipal.getId()))
	// 		.setIssuedAt(now)
	// 		.setExpiration(expiryDate)
	// 		.signWith(key)  // 변경된 부분
	// 		.compact();
	// }
	public String generateToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

		return Jwts.builder()
			.setSubject(userPrincipal.getProviderId()) // 내부 id("1") 대신 providerId 사용
			.claim("kakao_account", Map.of("profile", Map.of("nickname", userPrincipal.getNickname())))
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(key)
			.compact();
	}



	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parserBuilder()  // 변경된 부분
			.setSigningKey(key)               // 변경된 부분
			.build()
			.parseClaimsJws(token)
			.getBody();

		return Long.parseLong(claims.getSubject());
	}

	public String getProviderIdFromJWT(String token) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
		return claims.getSubject(); // subject가 providerId 문자열임
	}


	public boolean validateToken(String authToken) {
		try {
			Jwts.parserBuilder()             // 변경된 부분
				.setSigningKey(key)          // 변경된 부분
				.build()
				.parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}
}