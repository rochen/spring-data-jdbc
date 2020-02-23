package com.studio.harbour.jdbc.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.repository.UserRepository;

public class JwtTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtService jwtService;
	
	private String AUTHORIZATION_HEADER = "Authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {

		SecurityContext securityContext = SecurityContextHolder.getContext();

		Optional<String> token = getTokenFromHeader(request);
		if(token.isPresent()) {
			Optional<String> subject = jwtService.getSubject(token.get());
			if(subject.isPresent()) {
				if(securityContext.getAuthentication() == null) {
					Optional<User> user = userRepo.findByUsername(subject.get());
					if(user.isPresent()) {
						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
								user.get(), null, Collections.emptyList());
						securityContext.setAuthentication(auth);						
					}
				}
				
			}
		}
				
		filterChain.doFilter(request, response);
	}
	
	private Optional<String> getTokenFromHeader(HttpServletRequest request) {
		String authorization = request.getHeader(AUTHORIZATION_HEADER);
		if(authorization == null) {
			return Optional.empty();
		} else {
			String[] strings = StringUtils.tokenizeToStringArray(authorization, " ");
			return strings.length < 2? Optional.empty(): Optional.ofNullable(strings[1]);
		}
	}

}
