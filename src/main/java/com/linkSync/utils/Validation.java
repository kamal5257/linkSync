package com.linkSync.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linkSync.constant.APPServiceCode;
import com.linkSync.dto.UserDTO;
import com.linkSync.repository.UserRepository;



@Component
public class Validation {
	@Autowired
	ValidationUtil validateUtil;
	@Autowired
	UserRepository userRepository;
	

	public APPServiceCode validateUserDto(UserDTO user) {
		if (userRepository.findByUserName(user.getUsername()) != null) {
			return APPServiceCode.APP_008;
		}
		if (userRepository.findByEmail(user.getEmail()) != null) {
			return APPServiceCode.APP_007;
		}
		if (!validateUtil.isValidEmail(user.getEmail())) {
			return APPServiceCode.APP_002;
		}
		if (!(validateUtil.isValidUsername(user.getUsername()))) {
			return APPServiceCode.APP_003;
		}
		
		if (!(validateUtil.isValidPassword(user.getPassword()))) {
			return APPServiceCode.APP_004;
		}
		return null;
	}


	public static void main(String args[]) {
		Validation valid = new Validation();
		String test = "google.com,youtube.com,hello.com";
//        System.out.println( valid.isDomainValid( test ) );
	}
}
