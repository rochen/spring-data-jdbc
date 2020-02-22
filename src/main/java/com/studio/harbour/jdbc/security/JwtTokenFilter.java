package com.studio.harbour.jdbc.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.studio.harbour.jdbc.domain.User;
import com.studio.harbour.jdbc.repository.UserRepository;

public class JwtTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserRepository userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		
		if(securityContext.getAuthentication() == null) {
			// TODO: do real authentication
			User user = userService.findById(1L).get();
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			securityContext.setAuthentication(auth);
		}
		
		filterChain.doFilter(request, response);
	}

}
