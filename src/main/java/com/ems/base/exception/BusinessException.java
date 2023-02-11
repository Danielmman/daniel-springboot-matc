package com.ems.base.exception;

public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String errorCode) {
		super(errorCode);
	}

	public BusinessException(String errorCode, String message) {
		super(errorCode, message);
	}

	public BusinessException(String errorCode, String message, String[] errorParams) {
		super(errorCode, message, errorParams);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
}
