package com.ems.dto;

import com.ems.customdt.Achievement;
import com.ems.customdt.EmployeePosition;

import lombok.Data;

@Data
public class AdminUpdateEmployeeDTO {
	private EmployeePosition empPosition;
	private Achievement empAchievement;
}
