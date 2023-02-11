package com.ems.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.ems.base.exception.BusinessException;

/**
 * <h1>Employee Validator</h2>
 * 
 * @author Daniel
 *
 */
@Component
public class EmployeeValidator {
	private static final String MOBILE_NO_PATTERN = "[6-9][0-9]{9}";
	private static final String AADHAR_NO_PATTERN = "[0-9]{12}";

	/**
	 * <h2>Check Valid Mobile Number</h2>
	 * 
	 * Checks employee mobile number with mobile number RegEx pattern
	 * 
	 * @param mobileNo
	 * @throws BusinessException
	 */
	public void checkValidMobileNumber(String mobileNo) throws BusinessException {

		Pattern ptrn = Pattern.compile(MOBILE_NO_PATTERN);
		Matcher match = ptrn.matcher(mobileNo);

		if (!(match.find() && match.group().equals(mobileNo))) {

			throw new BusinessException("Invalid_Mobile_Number", MOBILE_NO_PATTERN, new String[] { mobileNo });
		}
	}

	/**
	 * <h2>Check Valid Aadhar Number</h2>
	 * 
	 * Checks employee aadhar number with aadhar number RegEx pattern
	 * 
	 * @param empAadharNumber
	 * @throws BusinessException
	 */
	public void checkValidAadharNumber(String empAadharNumber) throws BusinessException {

		Pattern ptrn = Pattern.compile(AADHAR_NO_PATTERN);
		Matcher match = ptrn.matcher(empAadharNumber);

		if (!(match.find() && match.group().equals(empAadharNumber))) {

			throw new BusinessException("Invalid_Aadhaar_Number", AADHAR_NO_PATTERN, new String[] { empAadharNumber });
		}
	}
}
