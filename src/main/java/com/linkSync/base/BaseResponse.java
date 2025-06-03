package com.linkSync.base;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class BaseResponse<T>
    implements
    Serializable
{
    @JsonInclude(Include.NON_NULL)
    private String  statusCode;
    @JsonInclude(Include.NON_NULL)
    private String  message;
    @JsonInclude(Include.NON_NULL)
    private List<T> data = null;
    private String  txnId;
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "BaseResponse [statusCode=" + statusCode + ", message=" + message + ", data=" + data + ", txnId=" + txnId
				+ "]";
	}
	public BaseResponse() {
		super();
	}
	public BaseResponse(String statusCode, String message, List<T> data, String txnId) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
		this.txnId = txnId;
	}
    
}
