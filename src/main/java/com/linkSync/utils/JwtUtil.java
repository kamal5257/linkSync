package com.linkSync.utils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.linkSync.model.AuthenticationResponseModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	final static Integer EXPIRY_TIME = (1000 * 60 * 60 * 24 * 2);
	final static Integer REFRESH_TIME = (1000 * 60 * 60 * 24 * 2);
	
	@Value("${token.secret_key}")
    private String SECRET_KEY;
	
	@Value("${token.type}")
	private String tokenType;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public AuthenticationResponseModel generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getUsername());
        AuthenticationResponseModel model = new AuthenticationResponseModel();
        model.setAccessToken(createToken(claims, userDetails.getUsername()));
        model.setRefreshToken(refreshToken(claims, userDetails.getUsername()));
        model.setTokenType(tokenType);
        model.setTokenMilliSec((System.currentTimeMillis()+EXPIRY_TIME.toString()));
        model.setTokenExpiry(new Date(System.currentTimeMillis()+EXPIRY_TIME).toString());
        return model;
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    
    private String refreshToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    
    
    public String expireToken(String token) {
        Map<String, Object> claims = new HashMap<>();
        return delToken(claims, token);
    }

    private String delToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 6 * 0))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}