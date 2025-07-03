package org.ezon.mall.exception;

public class FAQErrorResponse {
	private boolean success;
	private String message;
	private String code;
	
	public FAQErrorResponse(boolean success, String message, String code) {
		this.success = success;
		this.message = message;
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

	
}
