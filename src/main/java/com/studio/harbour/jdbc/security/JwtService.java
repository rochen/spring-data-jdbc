package com.studio.harbour.jdbc.security;

import java.security.Key;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.studio.harbour.jdbc.json.UserData;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
    public UserData getUserWithToken(UserData user) {
    	String subject = user.getUsername();    	    	
        String token = Jwts.builder().setSubject(subject).signWith(key).compact();
        user.setToken(token);
        
        return user;        
    }

    public Optional<String> getSubject(String token) {
    	JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();

    	Jws<Claims> claimsJws = parser.parseClaimsJws(token);
    	String subject = claimsJws.getBody().getSubject();
    	
        return Optional.ofNullable(subject);
    }
    
    
}
