package com.ems.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import com.ems.customdt.Achievement;
import com.ems.customdt.EmployeePosition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "empName", "empMail" }))
public class Employee {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String empId;
	@NotBlank
	private String empName;
	@Email
	private String empMail;
	private EmployeePosition empPosition;
	private LocalDate empJoiningDate;
	private Achievement empAchievement;
	private String empMobileNumber;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Person empPersonalDetail;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Address> employeeAddress;
}
