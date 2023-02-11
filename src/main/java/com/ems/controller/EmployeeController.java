package com.ems.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.base.exception.BusinessException;
import com.ems.base.exception.TechnicalException;
import com.ems.dto.AddressDTO;
import com.ems.dto.AdminUpdateEmployeeDTO;
import com.ems.dto.EmployeeDTO;
import com.ems.dto.GetEmployeeDTO;
import com.ems.dto.MessageDisplayDTO;
import com.ems.dto.PersonDTO;
import com.ems.model.Employee;
import com.ems.service.EmployeeService;

import lombok.extern.log4j.Log4j2;

/**
 * <h1>Employee CRUD API Controller</h1>
 * 
 * @author Daniel
 *
 */
@Log4j2
@RestController
@RequestMapping("/v1")
public class EmployeeController {

	@Autowired
	private EmployeeService userService;

	/**
	 * <h2>Admin Get Employee Page API</h2>
	 * 
	 * Get all employee with pagination (page number, page size and sort by)
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @return
	 * @throws BusinessException
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("")
	public List<Employee> adminGetEmployeePage(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "empName") String sortBy)
			throws BusinessException {
		List<Employee> listEmployee = null;
		try {
			listEmployee = userService.adminGetEmployeePage(pageNo, pageSize, sortBy).toList();
			log.debug("Admin-Controller-AdminGetEmployeePage-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Controller-AdminGetEmployeePage-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return listEmployee;
	}

	/**
	 *
	 * <h2>Admin Create Employee User Id API</h2>
	 * 
	 * Save employee detail (employee, person and address)
	 * 
	 * @param employee
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	@PostMapping("/admin/employee")
	@PreAuthorize("hasRole('ADMIN')")
	public MessageDisplayDTO adminCreateEmployeeUserId(@Valid @RequestBody EmployeeDTO employee)
			throws BusinessException, TechnicalException {
		MessageDisplayDTO messageResponse = null;
		try {
			messageResponse = userService.adminCreateEmployeeUserId(employee);
			log.debug("Admin-Controller-AdminCreateEmployeeUserId-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Controller-AdminCreateEmployeeUserId-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return messageResponse;
	}

	/**
	 * <h2>Admin Update Employee Detail API</h2>
	 * 
	 * Update employee's position and achievement
	 * 
	 * @param mail
	 * @param employee
	 * @return
	 * @throws BusinessException
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/admin/employee")
	public MessageDisplayDTO adminUpdateEmployeeDetail(@RequestParam String mail,
			@RequestBody AdminUpdateEmployeeDTO employee) throws BusinessException {
		MessageDisplayDTO messageDisplayDTO;
		try {
			messageDisplayDTO = userService.adminUpdateEmployeeDetail(mail, employee);
			log.debug("Admin-Controller-AdminUpdateEmployeeDetail-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Controller-AdminUpdateEmployeeDetail-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return messageDisplayDTO;
	}

	/**
	 * <h2>Admin Remove User API</h2>
	 * 
	 * Delete user with mail id
	 * 
	 * @param mail
	 * @return
	 * @throws BusinessException
	 */

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/admin/employee")
	public MessageDisplayDTO adminRemoveUser(@RequestParam String mail) throws BusinessException {
		String message;
		try {
			message = userService.adminRemoveUser(mail);
			log.debug("Admin-Controller-AdminRemoveUser-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Controller-AdminRemoveUser-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return new MessageDisplayDTO(message);
	}

	/**
	 * <h2>Admin Remove All User MongoDB API</h2>
	 * 
	 * Remove all users
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/admin/employee/all")
	public MessageDisplayDTO adminRemoveAllUserMongoDB() throws BusinessException {
		String message;
		try {
			message = userService.adminRemoveAllEmployeeMongoDB();
			log.debug("Admin-Controller-AdminRemoveAllUserMongoDB-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Controller-AdminRemoveAllUserMongoDB-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return new MessageDisplayDTO(message);
	}

	/**
	 * <h2>Admin View All Employee API</h2>
	 * 
	 * Display all employee
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@GetMapping("admin/employee/current")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Employee> adminViewAllEmployee() throws BusinessException {
		List<Employee> viewall = null;
		try {
			viewall = userService.adminViewAllEmployee();
			log.debug("Admin-Controller-AdminViewAllEmployee-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Controller-AdminViewAllEmployee-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return viewall;
	}

	/**
	 * <h2>Admin View All Employee MongoDB API</h2>
	 * 
	 * Display all employee
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@GetMapping("admin/employee/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Employee> adminViewAllEmployeeMongoDB() throws BusinessException {
		List<Employee> viewall = null;
		try {
			viewall = userService.adminViewAllEmployeeMongoDB();
			log.debug("Admin-Controller-AdminViewAllEmployeeMongoDB-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Controller-AdminViewAllEmployeeMongoDB-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return viewall;
	}

	/**
	 * <h2>Admin Get Employee by Mail Id API</h2>
	 * 
	 * Get particular employee detail with mail id
	 * 
	 * @param mail
	 * @return
	 * @throws BusinessException
	 */
	@GetMapping("admin/employee")
	@PreAuthorize("hasRole('ADMIN')")
	public GetEmployeeDTO adminGetEmployeeByMailId(String mail) throws BusinessException {
		GetEmployeeDTO empByMailId = null;
		try {
			empByMailId = userService.adminGetEmployeeByMailId(mail);
			log.debug("Admin-Controller-AdminGetEmployeeByMailId-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Controller-AdminGetEmployeeByMailId-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return empByMailId;
	}

	/**
	 * <h2>User View Employee Detail</h2>
	 * 
	 * Checks and display respective detail (employee,person and address) with
	 * authentication
	 * 
	 * @param auth
	 * @return
	 * @throws BusinessException
	 */
	@GetMapping("/employee")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public GetEmployeeDTO userViewEmployeeDetail(Authentication auth) throws BusinessException {
		GetEmployeeDTO respectivEmployeeDtl = null;
		try {
			respectivEmployeeDtl = userService.userViewEmployeeDetail(auth);
			log.debug("Employee-Controller-UserViewEmployeeDetail-Accessed");
		} catch (BusinessException e) {
			log.error("Employee-Controller-UserViewEmployeeDetail-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return respectivEmployeeDtl;
	}

	/**
	 * <h2>User Update Person Detail API</h2>
	 * 
	 * Update person detail (mobile number, gender, age, marital status and aadhar
	 * number) with authentication
	 * 
	 * @param authentication
	 * @param person
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/employee/person")
	public MessageDisplayDTO userUpdatePersonDtl(Authentication authentication, PersonDTO person)
			throws BusinessException, TechnicalException {
		MessageDisplayDTO messageDisplayDTO;
		try {
			messageDisplayDTO = userService.userUpdatePersonDtl(authentication, person);
			log.debug("Employee-Controller-UserUpdatePersonDetail-Accessed");
		} catch (BusinessException e) {
			log.error("Employee-Controller-UserUpdatePersonDetail-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return messageDisplayDTO;
	}

	/**
	 * <h2>User Update Address Detail API</h2>
	 * 
	 * Update address detail (permanent and temporary) with authentication
	 * 
	 * @param authentication
	 * @param address
	 * @return
	 * @throws BusinessException
	 */
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/employee/address")
	public MessageDisplayDTO userUpdateAddressDtl(Authentication authentication, AddressDTO address)
			throws BusinessException {
		String message;
		try {
			message = userService.userUpdateAddressDtl(authentication, address);
			log.debug("Employee-Controller-UserUpdateAddressDetail-Accessed");
		} catch (BusinessException e) {
			log.error("Employee-Controller-UserUpdateAddressDetail-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return new MessageDisplayDTO(message);
	}

}
