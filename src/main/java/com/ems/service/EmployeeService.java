package com.ems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ems.aspect.TraceExecuteTime;
import com.ems.base.exception.BusinessException;
import com.ems.base.exception.TechnicalException;
import com.ems.dao.EmployeeDaoEntry;
import com.ems.dto.AddressDTO;
import com.ems.dto.AdminUpdateEmployeeDTO;
import com.ems.dto.EmployeeDTO;
import com.ems.dto.GetEmployeeDTO;
import com.ems.dto.MessageDisplayDTO;
import com.ems.dto.PersonDTO;
import com.ems.model.Employee;
import com.ems.validation.EmployeeValidator;

import lombok.extern.log4j.Log4j2;

/**
 * <h1>Employee Service</h1>
 * 
 * @author Daniel
 *
 */
@Log4j2
@Component
public class EmployeeService {

	@Autowired
	private EmployeeValidator employeeValidator;

	@Autowired
	private EmployeeDaoEntry userDaoEntry;

	/**
	 * <h2>Admin Get Employee Page</h2>
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @return
	 * @throws BusinessException
	 */
	@TraceExecuteTime
	public Page<Employee> adminGetEmployeePage(Integer pageNo, Integer pageSize, String sortBy)
			throws BusinessException {

		Page<Employee> listEmployee = null;
		try {
			listEmployee = userDaoEntry.adminGetEmployeePage(PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
			log.debug("Admin-Service-AdminGetEmployeePage-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-AdminGetEmployeePage-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return listEmployee;
	}

	/**
	 * <h2>Admin Create Employee User Id</h2>
	 * 
	 * Validate employee mobile number and aadhar number
	 * 
	 * @param employee
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	@TraceExecuteTime
	public MessageDisplayDTO adminCreateEmployeeUserId(EmployeeDTO employee)
			throws BusinessException, TechnicalException {
		MessageDisplayDTO messageResponse = null;
		try {
			employeeValidator.checkValidMobileNumber(employee.getEmpMobileNumber());
			employeeValidator.checkValidAadharNumber(employee.getEmpPersonalDetail().getEmpAadharNumber());
			messageResponse = userDaoEntry.adminCreateEmployeeUserId(employee);
			log.debug("Admin-Service-AdminCreateEmployeeUserId-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-AdminCreateEmployeeUserId-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return messageResponse;
	}

	/**
	 * <h2>Admin Update Employee Detail</h2>
	 * 
	 * @param mail
	 * @param employee
	 * @return
	 * @throws BusinessException
	 */
	@TraceExecuteTime
	public MessageDisplayDTO adminUpdateEmployeeDetail(String mail, AdminUpdateEmployeeDTO employee)
			throws BusinessException {
		MessageDisplayDTO messageDisplayDTO;
		try {
			messageDisplayDTO = userDaoEntry.adminUpdateEmployeeDetail(mail, employee);
			log.debug("Admin-Service-AdminUpdateEmployeeDetail-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-AdminUpdateEmployeeDetail-Failed");

			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return messageDisplayDTO;
	}

	/**
	 * <h2>Admin Remove User</h2>
	 * 
	 * @param mail
	 * @return
	 * @throws BusinessException
	 */
	@TraceExecuteTime
	public String adminRemoveUser(String mail) throws BusinessException {
		String message;
		try {
			message = userDaoEntry.adminRemoveUser(mail);
			log.debug("Admin-Service-AdminRemoveUser-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-AdminRemoveUser-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return message;
	}

	/**
	 * <h2>Admin Remove All Employee MongoDB</h2>
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@TraceExecuteTime
	public String adminRemoveAllEmployeeMongoDB() throws BusinessException {
		String message;
		try {
			message = userDaoEntry.adminRemoveAllEmployeeMongoDB();
			log.debug("Admin-Service-AdminRemoveAllEmployeeMongoDB-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-AdminRemoveAllEmployeeMongoDB-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return message;
	}

	/**
	 * <h2>Admin View All Employee</h2>
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@TraceExecuteTime
	public List<Employee> adminViewAllEmployee() throws BusinessException {
		List<Employee> viewall = null;
		try {
			viewall = userDaoEntry.adminViewAllEmployee();
			log.debug("Admin-Service-AdminViewAllEmployee-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-AdminViewAllEmployee-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return viewall;
	}

	/**
	 * <h2>Admin View All Employee MongoDB</h2>
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@TraceExecuteTime
	public List<Employee> adminViewAllEmployeeMongoDB() throws BusinessException {
		List<Employee> viewall = null;
		try {
			viewall = userDaoEntry.adminViewAllEmployeeMongoDB();
			log.debug("Admin-Service-AdminViewAllEmployeeMongoDB-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-AdminViewAllEmployeeMongoDB-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return viewall;
	}

	/**
	 * <h2>Admin Get Employee by Mail Id</h2>
	 * 
	 * @param mail
	 * @return
	 * @throws BusinessException
	 */
	@TraceExecuteTime
	public GetEmployeeDTO adminGetEmployeeByMailId(String mail) throws BusinessException {
		GetEmployeeDTO empByMailId = null;
		try {
			empByMailId = userDaoEntry.adminGetEmployeeByMailId(mail);
			log.debug("Admin-Service-AdminGetEmployeeByMailId-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-AdminGetEmployeeByMailId-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return empByMailId;
	}

	/**
	 * <h2>User View Employee Detail</h2>
	 * 
	 * @param auth
	 * @return
	 * @throws BusinessException
	 */
	@TraceExecuteTime
	public GetEmployeeDTO userViewEmployeeDetail(Authentication auth) throws BusinessException {
		GetEmployeeDTO respectivEmployeeDtl = null;
		try {
			respectivEmployeeDtl = userDaoEntry.userViewEmployeeDetail(auth);
			log.debug("Admin-Service-UserViewEmployeeDetail-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-UserViewEmployeeDetail-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return respectivEmployeeDtl;
	}

	/**
	 * <h2>User Update Address Detail</h2>
	 * 
	 * @param authentication
	 * @param address
	 * @return
	 * @throws BusinessException
	 */
	@TraceExecuteTime
	public String userUpdateAddressDtl(Authentication authentication, AddressDTO address) throws BusinessException {
		String message;
		try {
			message = userDaoEntry.userUpdateAddressDtl(authentication, address);
			log.debug("Admin-Service-UserUpdateAddressDetail-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-UserUpdateAddressDetail-Failed", e.getMessage());
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return message;
	}

	/**
	 * <h2>User Update Person Detail</h2>
	 * 
	 * Validate employee mobile number and aadhar number
	 * 
	 * @param authentication
	 * @param person
	 * @return
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	@TraceExecuteTime
	public MessageDisplayDTO userUpdatePersonDtl(Authentication authentication, PersonDTO person)
			throws BusinessException, TechnicalException {
		MessageDisplayDTO messageDisplayDTO;
		try {
			messageDisplayDTO = userDaoEntry.userUpdatePersonDtl(authentication, person);
			log.debug("Admin-Service-UserUpdatePersonDetail-Accessed");
		} catch (BusinessException e) {
			log.error("Admin-Service-UserUpdatePersonDetail-Failed");
			throw new BusinessException(e.getErrorCode(), e.getMessage(), e.getErrorParams());
		}
		return messageDisplayDTO;
	}
}
