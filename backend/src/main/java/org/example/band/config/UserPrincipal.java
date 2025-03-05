// package org.example.band.config;
//
// import java.util.Collection;
// import java.util.Collections;
// import java.util.List;
// import java.util.Map;
//
// import org.example.band.entity.User;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.oauth2.core.user.OAuth2User;
//
// import lombok.Getter;
//
// @Getter
// public class UserPrincipal implements OAuth2User, UserDetails {
// 	private Long id;
// 	private String email;
// 	private String password;
// 	private Collection<? extends GrantedAuthority> authorities;
// 	private Map<String, Object> attributes;
//
// 	public UserPrincipal(Long id, String email, String password,
// 		Collection<? extends GrantedAuthority> authorities) {
// 		this.id = id;
// 		this.email = email;
// 		this.password = password;
// 		this.authorities = authorities;
// 	}
//
// 	public static UserPrincipal create(User user) {
// 		List<GrantedAuthority> authorities = Collections.
// 			singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//
// 		return new UserPrincipal(
// 			user.getId(),
// 			user.getEmail(),
// 			"",
// 			authorities
// 		);
// 	}
//
// 	public static UserPrincipal create(User user, Map<String, Object> attributes) {
// 		UserPrincipal userPrincipal = UserPrincipal.create(user);
// 		userPrincipal.setAttributes(attributes);
// 		return userPrincipal;
// 	}
//
// 	// UserDetails 구현
// 	@Override
// 	public String getUsername() {
// 		return email;
// 	}
//
// 	@Override
// 	public String getPassword() {
// 		return password;
// 	}
//
// 	@Override
// 	public boolean isAccountNonExpired() {
// 		return true;
// 	}
//
// 	@Override
// 	public boolean isAccountNonLocked() {
// 		return true;
// 	}
//
// 	@Override
// 	public boolean isCredentialsNonExpired() {
// 		return true;
// 	}
//
// 	@Override
// 	public boolean isEnabled() {
// 		return true;
// 	}
//
// 	// OAuth2User 구현
// 	@Override
// 	public Map<String, Object> getAttributes() {
// 		return attributes;
// 	}
//
// 	@Override
// 	public Collection<? extends GrantedAuthority> getAuthorities() {
// 		return authorities;
// 	}
//
// 	@Override
// 	public String getName() {
// 		return String.valueOf(id);
// 	}
//
// 	public void setAttributes(Map<String, Object> attributes) {
// 		this.attributes = attributes;
// 	}
// }

package org.example.band.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.example.band.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

@Getter
public class UserPrincipal implements OAuth2User, UserDetails {
	private Long id;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	// 추가 필드: providerId와 nickname
	private String providerId;
	private String nickname;

	// 생성자 수정: providerId와 nickname 포함
	public UserPrincipal(Long id, String email, String password,
		Collection<? extends GrantedAuthority> authorities,
		String providerId, String nickname) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.providerId = providerId;
		this.nickname = nickname;
	}

	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = Collections.singletonList(
			new SimpleGrantedAuthority("ROLE_USER")
		);
		// User 엔티티에 providerId와 nickname 필드가 있다고 가정합니다.
		return new UserPrincipal(
			user.getId(),
			user.getEmail(),
			"",
			authorities,
			user.getProviderId(),  // User 엔티티의 providerId 사용
			user.getNickname()       // User 엔티티의 name 사용
		);
	}

	public static UserPrincipal create(User user, Map<String, Object> attributes) {
		UserPrincipal userPrincipal = UserPrincipal.create(user);
		userPrincipal.setAttributes(attributes);
		return userPrincipal;
	}

	// UserDetails 구현
	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	// OAuth2User 구현
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return String.valueOf(id);
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	// 추가한 getter가 자동으로 생성됨(@Getter 사용 시)
	// 예: getProviderId()와 getNickname()
}
