package com.ems.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.ems.base.exception.BusinessException;
import com.ems.base.exception.TechnicalException;
import com.ems.dto.AddressDTO;
import com.ems.dto.AdminUpdateEmployeeDTO;
import com.ems.dto.EmployeeDTO;
import com.ems.dto.GetEmployeeDTO;
import com.ems.dto.MessageDisplayDTO;
import com.ems.dto.PersonDTO;
import com.ems.model.Employee;

/**
 * <h1>Employee DAO Entry Interface</h1>
 * 
 * @author Daniel
 *
 */
public interface EmployeeDaoEntry {
	/**
	 * <h2>Admin Get Employee Page</h2>
	 * 
	 * @param paging
	 * @return
	 * @throws BusinessException
	 */
	public Page<Employee> adminGetEmployeePage(Pageable paging) throws BusinessException;

	/**
	 * <h2>Admin Create Employee User Id</h2>
	 * 
	 * Create work mail from user id
	 * 
	 * Save employee detail in SQL and NoSQL database
	 * 
	 * @param employee
	 * @return
	 * @throws BusinessException
	 */
	public MessageDisplayDTO adminCreateEmployeeUserId(EmployeeDTO employee) throws BusinessException;

	/**
	 * <h2>Admin Update Employee Detail</h2>
	 * 
	 * Check and update valid employee's detail
	 * 
	 * @param mail
	 * @param employee
	 * @return
	 * @throws BusinessException
	 */
	public MessageDisplayDTO adminUpdateEmployeeDetail(String mail, AdminUpdateEmployeeDTO employee)
			throws BusinessException;

	/**
	 * <h2>Admin Remove User</h2>
	 * 
	 * Check and remove user with mail id if exits
	 * 
	 * @param mail
	 * @return
	 * @throws BusinessException
	 */
	public String adminRemoveUser(String mail) throws BusinessException;

	/**
	 * <h2>Admin Remove All Employee MongoDB</h2>
	 * 
	 * Remove all employee from MongoDB database
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String adminRemoveAllEmployeeMongoDB() throws BusinessException;

	/**
	 * <h2>Admin View All Employee</h2>
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<Employee> adminViewAllEmployee() throws BusinessException;

	/**
	 * <h2>Admin View All Employee MongoDB</h2>
	 * 
	 * @return
	 * @throws BusinessException
	 */
	List<Employee> adminViewAllEmployeeMongoDB() throws BusinessException;

	/**
	 * <h2>Admin Get Employee by Mail Id</h2>
	 * 
	 * @param mail
	 * @return
	 * @throws BusinessException
	 */
	public GetEmployeeDTO adminGetEmployeeByMailId(String mail) throws BusinessException;

	/**
	 * <h2>User View Employee Detail</h2>
	 * 
	 * @param auth
	 * @return
	 * @throws BusinessException
	 */
	public GetEmployeeDTO userViewEmployeeDetail(Authentication auth) throws BusinessException;

	/**
	 * <h2>User Update Address Detail</h2>
	 * 
	 * @param authentication
	 * @param address
	 * @return
	 * @throws BusinessException
	 */
	public String userUpdateAddressDtl(Authentication authentication, AddressDTO address) throws BusinessException;

	/**
	 * <h2>User Update Person Detail</h2>
	 * 
	 * @param authentication
	 * @param person
	 * @return
	 * @throws BusinessException
	 */
	public MessageDisplayDTO userUpdatePersonDtl(Authentication authentication, PersonDTO person)
			throws BusinessException, TechnicalException;

}
