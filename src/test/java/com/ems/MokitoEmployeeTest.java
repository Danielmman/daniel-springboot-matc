package com.ems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ems.base.exception.BusinessException;
import com.ems.controller.EmployeeController;
import com.ems.customdt.Achievement;
import com.ems.customdt.EmployeePosition;
import com.ems.dao.EmployeeDaoEntry;
import com.ems.dto.GetEmployeeDTO;
import com.ems.model.Employee;
import com.ems.service.EmployeeService;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class MockitoEmployeeTest {

	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeController employeeController;

	@Mock
	private EmployeeDaoEntry employeeDao;

	@Test
	void adminGetEmployeeByMailId() throws BusinessException {
		GetEmployeeDTO employee = new GetEmployeeDTO(null,
				new Employee("f662b495-dbb1-4e8b-af6d-b501bd570003", null, null, null, null, null, null, null, null));
		String mailId = "daniel@ems.com";
		when(employeeDao.adminGetEmployeeByMailId(mailId)).thenReturn(employee);

		GetEmployeeDTO result = employeeService.adminGetEmployeeByMailId(mailId);

		assertThat(result.getEmployeeDtl().getEmpId()).isEqualTo("f662b495-dbb1-4e8b-af6d-b501bd570003");
	}

	@Test
	void getAllEmployee() throws BusinessException {
		Employee employee = new Employee(RandomString.make(5), RandomString.make(5), RandomString.make(4) + "ems.com",
				EmployeePosition.CEO, LocalDate.now(), Achievement.LEVEL_1, "9988776655", null, null);
		Employee employee1 = new Employee(RandomString.make(5), RandomString.make(5), RandomString.make(4) + "ems.com",
				EmployeePosition.DIRECTOR, LocalDate.now(), Achievement.LEVEL_5, "8877665577", null, null);
		List<Employee> employees = new ArrayList<>();
		employees.add(0, employee);
		employees.add(1, employee1);
		when(employeeDao.adminViewAllEmployee()).thenReturn(employees);

		List<Employee> result = employeeService.adminViewAllEmployee();

		assertThat(result.get(0).getEmpMail()).isEqualTo(employee.getEmpMail());
		assertThat(result.get(1).getEmpMobileNumber()).isEqualTo(employee1.getEmpMobileNumber());
	}

}