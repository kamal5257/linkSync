package com.linkSync.model;

import lombok.Data;

@Data
public class AuthenticationResponseModel {
	String tokenType;
	String accessToken;
	String refreshToken;	
	String tokenExpiry;
	String tokenMilliSec;
}
