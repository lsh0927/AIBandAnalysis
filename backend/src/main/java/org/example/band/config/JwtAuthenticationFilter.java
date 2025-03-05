package org.example.band.config;

import java.io.IOException;

import org.example.band.entity.User;
import org.example.band.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider tokenProvider;
	private final UserRepository userRepository;

	// @Override
	// protected void doFilterInternal(HttpServletRequest request,
	// 	HttpServletResponse response, FilterChain filterChain)
	// 	throws ServletException, IOException {
	// 	try {
	// 		String jwt = getJwtFromRequest(request);
	//
	// 		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
	// 			Long userId = tokenProvider.getUserIdFromJWT(jwt);
	// 			User user = userRepository.findById(userId)
	// 				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	//
	// 			UserDetails userDetails = UserPrincipal.create(user);
	// 			UsernamePasswordAuthenticationToken authentication =
	// 				new UsernamePasswordAuthenticationToken(userDetails, null,
	// 					userDetails.getAuthorities());
	// 			authentication.setDetails(new WebAuthenticationDetailsSource()
	// 				.buildDetails(request));
	//
	// 			SecurityContextHolder.getContext()
	// 				.setAuthentication(authentication);
	// 		}
	// 	} catch (Exception ex) {
	// 		logger.error("Could not set user authentication in security context", ex);
	// 	}
	//
	// 	filterChain.doFilter(request, response);
	// }
	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				// 변경: getProviderIdFromJWT()로 providerId 문자열을 얻음
				String providerId = tokenProvider.getProviderIdFromJWT(jwt);
				// providerId를 기준으로 사용자 조회
				User user = userRepository.findByProviderId(providerId)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with providerId: " + providerId));

				UserDetails userDetails = UserPrincipal.create(user);
				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource()
					.buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}

		filterChain.doFilter(request, response);
	}


	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}