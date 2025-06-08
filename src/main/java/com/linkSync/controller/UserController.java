package com.linkSync.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linkSync.base.BaseResponse;
import com.linkSync.constant.APPServiceCode;
import com.linkSync.dto.ForgotPasswordDto;
import com.linkSync.dto.OtpVerificationRequest;
import com.linkSync.dto.ResetPasswordRequest;
import com.linkSync.dto.UserDTO;
import com.linkSync.dto.UserLoginDTO;
import com.linkSync.services.UserService;
import com.linkSync.utils.SSLUtil;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	private final static String AUTHENTICATE = "/authenticate";
	private final static String FORGOT_PASSWORD = "/forgotPassword";
	private final static String REGISTER = "/register";
	final static Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = REGISTER, method = RequestMethod.POST)
	public BaseResponse<Object> createUser(@RequestBody UserDTO userDTO) {
		BaseResponse<Object> response = new BaseResponse<>();
		APPServiceCode serviceCode = userService.registerNewUser(userDTO);
		response.setMessage(serviceCode.getStatusDesc());
		response.setStatusCode(serviceCode.getStatusCode());
		return response;
	}

	@RequestMapping(value = AUTHENTICATE, method = { RequestMethod.POST })
	public BaseResponse<Object> authenticate(@RequestHeader Map httpHeaders, @RequestBody @Valid UserLoginDTO request,
			BindingResult inBindingResult) {
		APPServiceCode code = null;
		BaseResponse<Object> result = new BaseResponse<Object>();
		try {
			if (inBindingResult.hasErrors()) {
				FieldError fieldError = inBindingResult.getFieldErrors().get(0);
				logger.info("Error Code:" + fieldError.getDefaultMessage());
				code = APPServiceCode.valueOf(fieldError.getDefaultMessage());
				result.setStatusCode(code.getStatusCode());
				result.setMessage(code.getStatusDesc());
			} else {
				result = userService.loginUser(request);
				logger.error("INSIDE:::_USER_CONTROLLER");
			}
		} catch (Exception e) {
			logger.error("Error in deactivate project controller", e);
			code = APPServiceCode.APP_999;
			result.setStatusCode(code.getStatusCode());
			result.setMessage(code.getStatusDesc());
		}
		return result;
	}

	@RequestMapping(value = FORGOT_PASSWORD, method = { RequestMethod.POST })
	public BaseResponse<Object> forgotPassword(@RequestHeader Map httpHeaders,
			@RequestBody @Valid ForgotPasswordDto request, BindingResult inBindingResult) {
		APPServiceCode code = null;
		BaseResponse<Object> result = new BaseResponse<Object>();
		SSLUtil.disableSSLCertificateChecking();
		try {
			if (inBindingResult.hasErrors()) {
				FieldError fieldError = inBindingResult.getFieldErrors().get(0);
				logger.info("Error Code:" + fieldError.getDefaultMessage());
				code = APPServiceCode.valueOf(fieldError.getDefaultMessage());
				result.setStatusCode(code.getStatusCode());
				result.setMessage(code.getStatusDesc());
			} else {
				result = userService.forgotPassword(request);
			}
		} catch (Exception e) {
			logger.error("Error in deactivate project controller", e);
			code = APPServiceCode.APP_999;
			result.setStatusCode(code.getStatusCode());
			result.setMessage(code.getStatusDesc());
		}
		return result;
	}

	@PostMapping("/verify-otp")
	public BaseResponse<Object> verifyOtp(@RequestBody OtpVerificationRequest request) {
		return userService.verifyOtp(request);
	}

	@PostMapping("/reset-password")
	public BaseResponse<Object> resetPassword(@RequestBody ResetPasswordRequest request) {
		return userService.resetPassword(request);
	}

	@PostMapping("/user")
	public BaseResponse<Object> getUser(HttpServletRequest request) {
		BaseResponse<Object> result = new BaseResponse<Object>();
		APPServiceCode code = null;
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			code = APPServiceCode.APP_909;
		}

		String token = authHeader.substring(7);
		result = userService.getUserFromToken(token);

		if (result == null) {
			code = APPServiceCode.APP_910;
		}

		return result;
	}

}

@Getter
class AuthRequest {
	private String username;
	private String password;
}

@Getter
@Setter
class AuthResponse {
	private String token;

	public AuthResponse(String token) {
		this.token = token;
	}
}
