package com.linkSync.repository;

import org.springframework.data.repository.CrudRepository;

import com.linkSync.entity.AuthUserTokens;


public interface TokenRepository extends CrudRepository<AuthUserTokens, Long>{

	public AuthUserTokens getByUserId(String userId);
	public AuthUserTokens getByToken(String token);
	public AuthUserTokens getByUserIdAndToken(String userId, String token);
}
