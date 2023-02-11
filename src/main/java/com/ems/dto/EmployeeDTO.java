package com.ems.dto;

import java.time.LocalDate;
import java.util.List;

import com.ems.customdt.Achievement;
import com.ems.customdt.EmployeePosition;
import com.ems.model.Address;
import com.ems.model.Person;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {
	private String empName;
	private EmployeePosition empPosition;
	private LocalDate empJoiningDate;
	private Achievement empAchievement;
	private String empMobileNumber;
	private Person empPersonalDetail;
	private List<Address> employeeAddress;
}
