package org.ezon.mall.exception;

public class FAQException extends RuntimeException{
	private final FAQErrorCode errorCode;

	public FAQException(String message, FAQErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public FAQErrorCode getErrorCode() {
		return errorCode;
	}
	

}
