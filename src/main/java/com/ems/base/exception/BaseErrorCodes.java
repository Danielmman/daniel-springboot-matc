package com.ems.base.exception;

public enum BaseErrorCodes {

	GENERAL_EXCEPTION("General exception"), TECHNICAL_EXCEPTION("Technical exception"), BAD_REQUEST("Invalid request"),
	MISSING_HEADER_FIELDS("Missing mandatory header fields"), INVALID_HEADER_FIELDS("Invalid header fields"),
	MISSING_MANDATORY_FIELDS("Missing mandatory input fields"), FIELDS_VALIDATION_EXCEPTION("Invalid input values"),
	RECORD_NOT_FOUND("Record not found"), DUPLICATE_RECORD("Duplicate record found");

	private String message;

	private BaseErrorCodes(String message) {
		this.message = message;
	}

	private BaseErrorCodes() {
		message = this.name();
	}

	public String message() {
		return message;
	}
}
