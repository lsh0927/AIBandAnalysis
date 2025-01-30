package org.example.band.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	@PostMapping("/logout")
	@ResponseStatus(HttpStatus.OK)
	public void logout() {
		// 로그아웃 처리만 하고 200 OK 반환
	}
}
