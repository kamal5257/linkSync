package com.linkSync.services;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.linkSync.base.BaseResponse;
import com.linkSync.constant.APPServiceCode;
import com.linkSync.dto.ForgotPasswordDto;
import com.linkSync.dto.OtpVerificationRequest;
import com.linkSync.dto.ResetPasswordRequest;
import com.linkSync.dto.UserDTO;
import com.linkSync.dto.UserLoginDTO;
import com.linkSync.entity.AuthUserTokens;
import com.linkSync.entity.OtpHistory;
import com.linkSync.entity.User;
import com.linkSync.model.AuthenticationResponseModel;
import com.linkSync.model.EmailDetails;
import com.linkSync.repository.OtpHistoryRepo;
import com.linkSync.repository.TokenRepository;
import com.linkSync.repository.UserRepository;
import com.linkSync.utils.AppEncryptionUtil;
import com.linkSync.utils.EmailUtil;
import com.linkSync.utils.JwtUtil;
import com.linkSync.utils.StringUtils;
import com.linkSync.utils.Validation;

@Service
public class UserService {
	final static Logger logger = LogManager.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailService userDetailService;

	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	OtpHistoryRepo otpHistoryRepo;
	@Autowired
	Validation validation;
	

	public APPServiceCode registerNewUser(UserDTO userDTO) {
		APPServiceCode serviceCode = null;

		try {
			serviceCode = validation.validateUserDto(userDTO);
			if (!StringUtils.isValidObj(serviceCode)) {

				// Create a new user
				User user = new User();
				user.setUsername(userDTO.getUsername());
				user.setEmail(userDTO.getEmail());
				user.setPasswordHash(AppEncryptionUtil.encrypt(userDTO.getPassword()));
				userRepository.save(user);
				serviceCode = APPServiceCode.APP_001;
			}
		} catch (Exception e) {
			System.out.println(e);
			serviceCode = APPServiceCode.APP_999;
		}
		return serviceCode;

	}

	public BaseResponse<Object> loginUser(UserLoginDTO request) {
		{
			APPServiceCode code = APPServiceCode.APP_998;
			BaseResponse<Object> response = new BaseResponse<Object>();
			try {
				byte[] decoded = Base64.decodeBase64(request.getPassword());
				String reqPwd = new String(decoded, StandardCharsets.UTF_8);

				this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
						AppEncryptionUtil.encrypt(reqPwd)));
				final UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
				if (!StringUtils.isValidObj(userDetails)) {
					code = APPServiceCode.APP_998;
				}
				final AuthenticationResponseModel jwt = jwtUtil.generateToken(userDetails);
				if (jwt != null) {
					AuthUserTokens userToken = new AuthUserTokens();
					userToken.setUserId(request.getUsername());
					userToken.setToken(jwt.getAccessToken());
					tokenRepository.save(userToken);
					List<Object> responseObject = new ArrayList<>();
//					responseObject.add(jwt);
					Map<String, Object> jwtObj = new HashMap<>();
					jwtObj.put("token", jwt);
					response.setData(jwtObj);
				}
				code = APPServiceCode.APP_001;
			} catch (Exception e) {
				logger.error(e.getCause(), e);
			} finally {
				response.setStatusCode(code.getStatusCode());
				response.setMessage(code.getStatusDesc());
			}
			return response;
		}
	}

	public BaseResponse<Object> forgotPassword(ForgotPasswordDto request) {
		BaseResponse<Object> response = new BaseResponse<Object>();
		APPServiceCode code = APPServiceCode.APP_999;
		try {
			User user = userRepository.findByUserName(request.getUsername());
			System.out.println("USER_DETAILS :::: " + user + " ___ " + request.getUsername());
			if (StringUtils.isValidObj(user)) {
				int otp = new Random().nextInt(900000) + 100000;
				int leftLimit = 48; // numeral '0'
				int rightLimit = 122; // letter 'z'
				int targetStringLength = 20;
				Random random = new Random();
				String txnId = random.ints(leftLimit, rightLimit + 1)
						.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
						.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
				logger.info(otp + " : " + txnId);
				OtpHistory otpHistory = new OtpHistory();
				otpHistory.setOtp(otp + "");
				otpHistory.setTxnId(txnId);
				otpHistory.setReceiverDetail(user.getEmail());
				otpHistory = otpHistoryRepo.save(otpHistory);
				if (StringUtils.isValidObj(otpHistory.getHistoryId()) && otpHistory.getHistoryId() > 0) {
					EmailDetails detail = new EmailDetails();
					detail.setMsgBody("Your OTP is: " + otp);
					detail.setSubject("Forgot Password");
					detail.setRecipient(user.getEmail());
					System.out.println("EMAILLL ::: \n\n " + detail);
					Boolean email = emailUtil.sendSimpleMail(detail);
					if (email) {
						response.setTxnId(txnId);
						code = APPServiceCode.APP_001;
					}
				}
			} else {
				code = APPServiceCode.APP_908;
			}
		} catch (Exception e) {
			logger.error(e.getCause(), e);
		} finally {
			response.setStatusCode(code.getStatusCode());
			response.setMessage(code.getStatusDesc());
		}
		return response;
	}
	

	// 1. Verify OTP
	public BaseResponse<Object> verifyOtp(OtpVerificationRequest request) {
		BaseResponse<Object> response = new BaseResponse<>();
		APPServiceCode code = APPServiceCode.APP_999;

		OtpHistory record = otpHistoryRepo.findByTxnIdAndOtp(request.getTxnId(), request.getOtp());
		if (record != null) {
			// (Optional) Check OTP expiry
			code = APPServiceCode.APP_001;
			response.setTxnId(record.getTxnId());
		} else {
			code = APPServiceCode.APP_904; // Invalid OTP
		}

		response.setStatusCode(code.getStatusCode());
		response.setMessage(code.getStatusDesc());

		return response;
	}

	// 2. Reset Password after OTP verification
	public BaseResponse<Object> resetPassword(ResetPasswordRequest request) {
		BaseResponse<Object> response = new BaseResponse<>();
		APPServiceCode code = APPServiceCode.APP_999;

		OtpHistory record = otpHistoryRepo.findByTxnId(request.getTxnId());
		if (record != null) {
			User user = userRepository.findByUserName(request.getUsername());
			if (user != null && user.getEmail().equalsIgnoreCase(record.getReceiverDetail())) {
				user.setPasswordHash(AppEncryptionUtil.encrypt(request.getNewPassword()));
				userRepository.save(user);
				code = APPServiceCode.APP_001;
			} else {
				code = APPServiceCode.APP_908;
			}
		} else {
			code = APPServiceCode.APP_904;
		}

		response.setStatusCode(code.getStatusCode());
		response.setMessage(code.getStatusDesc());
		return response;
	}
	
	public BaseResponse<Object> getUserFromToken(String token) {
		BaseResponse<Object> response = new BaseResponse<>();
        String username = jwtUtil.extractUsername(token);
        User userOpt = userRepository.findByUserName(username);

        if (!StringUtils.isValidObj(userOpt)) return null;
        userOpt.setPasswordHash(null);
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        if (!jwtUtil.validateToken(token, userDetails)) return null;

        List<Object> userData = new ArrayList<>();
        userData.add(userOpt);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("user", userOpt);
        userMap.put("org", null);
        userMap.put("projects", null);
        userMap.put("invitation", null);
        userMap.put("plan", null);
        response.setData(userMap);
        return response;
    }

}
