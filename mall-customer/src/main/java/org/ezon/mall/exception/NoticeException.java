package org.ezon.mall.exception;

public class NoticeException extends RuntimeException{
	private final NoticeErrorCode errorCode;

	public NoticeException(String message, NoticeErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public NoticeErrorCode getErrorCode() {
		return errorCode;
	}
	
}
