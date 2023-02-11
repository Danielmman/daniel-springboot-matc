package com.ems.dto;

import com.ems.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeDTO {
	private String message;
	private Employee employeeDtl;
}
