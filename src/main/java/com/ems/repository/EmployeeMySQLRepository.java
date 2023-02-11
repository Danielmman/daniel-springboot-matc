package com.ems.repository;

import javax.persistence.Id;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.model.Employee;

public interface EmployeeMySQLRepository extends JpaRepository<Employee, Id> {

	public Employee findByEmpName(String empName);

	public Employee findByEmpMail(String empMail);
}
