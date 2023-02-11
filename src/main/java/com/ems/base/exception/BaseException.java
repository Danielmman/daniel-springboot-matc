package com.ems.base.exception;

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;
	protected String errorCode;
	protected String[] errorParams;

	public BaseException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public BaseException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseException(String errorCode, String message, String[] errorParams) {
		super(message);
		this.errorCode = errorCode;
		this.errorParams = errorParams;
	}

	public BaseException(Throwable cause) {
		super(cause);
		if (cause instanceof BaseException) {
			BaseException ex = ((BaseException) cause);
			setErrorCode(ex.getErrorCode());
			setErrorParams(ex.getErrorParams());
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String[] getErrorParams() {
		return errorParams;
	}

	public void setErrorParams(String[] errorParams) {
		this.errorParams = errorParams;
	}

	public String toString() {
		String s = getClass().getName() + ": (" + errorCode + ")";
		String message = getLocalizedMessage();
		return (message != null) ? (s + " " + message) : s;
	}

}
