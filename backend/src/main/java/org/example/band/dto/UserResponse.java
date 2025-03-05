package org.example.band.dto;

import org.example.band.entity.User;


public record UserResponse(
	String id,
	String email,
	String nickname,
	String profileImage,
	String provider,
	String providerId
) {
	public static UserResponse from(org.example.band.entity.User user) {
		return new UserResponse(
			String.valueOf(user.getId()),
			user.getEmail(),
			user.getNickname(),
			user.getProfileImage(),
			user.getProvider().toString(),
			user.getProviderId()
		);
	}
}

