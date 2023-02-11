package com.ems.base.exception;

public class TechnicalException extends BaseException {

	private static final long serialVersionUID = 1L;

	public TechnicalException(String errorCode) {
		super(errorCode);
	}

	public TechnicalException(String errorCode, String message) {
		super(errorCode, message);
	}

	public TechnicalException(String errorCode, String message, String[] errorParams) {
		super(errorCode, message, errorParams);
	}

	public TechnicalException(Throwable cause) {
		super(cause);
	}
}