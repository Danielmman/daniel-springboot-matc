package com.ems.dto;

import com.ems.customdt.Gender;
import com.ems.customdt.MaritalStatus;

import lombok.Data;

@Data
public class PersonDTO {
	private String empMobileNumber;
	private Gender empGender;
	private Integer empAge;
	private MaritalStatus empMaritalStatus;
	private String empAadharNumber;
}
