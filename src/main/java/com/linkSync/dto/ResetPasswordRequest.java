package com.linkSync.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
	private String username;
    private String txnId;
    private String newPassword;
}
