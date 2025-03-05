package org.example.band.controller;

import org.example.band.dto.UserResponse;
import org.example.band.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserRepository userRepository;

	@GetMapping("/{providerId}")
	public ResponseEntity<UserResponse> getUser(@PathVariable String providerId) {
		UserResponse userResponse = userRepository.findByProviderId(providerId)
			.map(UserResponse::from)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		return ResponseEntity.ok(userResponse);
	}
}
