package com.ems.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ems.base.exception.BusinessException;
import com.ems.base.exception.TechnicalException;
import com.ems.dto.AddressDTO;
import com.ems.dto.AdminUpdateEmployeeDTO;
import com.ems.dto.EmployeeDTO;
import com.ems.dto.GetEmployeeDTO;
import com.ems.dto.MessageDisplayDTO;
import com.ems.dto.PersonDTO;
import com.ems.model.Address;
import com.ems.model.Employee;
import com.ems.repository.EmployeeMongoDBRepository;
import com.ems.repository.EmployeeMySQLRepository;
import com.ems.repository.UserRepository;
import com.ems.validation.EmployeeValidator;

import lombok.extern.log4j.Log4j2;

/**
 * <h1>Employee DAO Implementation</h1>
 * 
 * @author Daniel
 *
 */
@Log4j2
@Component
public class EmployeeDao implements EmployeeDaoEntry {

	@Autowired
	private EmployeeMySQLRepository employeeMySQLRepository;

	@Autowired
	private EmployeeMongoDBRepository employeeMongoDBRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeValidator employeeValidator;

	@Override
	public Page<Employee> adminGetEmployeePage(Pageable paging) {
		log.debug("Admin-Dao-AdminGetEmployeePage-Accessed");
		return employeeMySQLRepository.findAll(paging);
	}

	@Override
	public MessageDisplayDTO adminCreateEmployeeUserId(EmployeeDTO employee) throws BusinessException {
		try {
			Employee saveEmployee = new Employee();
			saveEmployee.setEmpMail(employee.getEmpName() + "@ems.com");
			BeanUtils.copyProperties(employee, saveEmployee);
			employeeMySQLRepository.save(saveEmployee);
			employeeMongoDBRepository.save(saveEmployee);
			log.debug("Admin-Dao-AdminCreateEmployeeUserId-Accessed");
		} catch (Exception e) {
			log.error("Admin-Dao-AdminCreateEmployeeUserId-Failed");
			throw new BusinessException("Employee_Name_Already_Registered", e.getMessage(),
					new String[] { employee.getEmpName() });
		}
		return new MessageDisplayDTO(employee.getEmpName() + " detail saved succussfully");
	}

	@Override
	public String adminRemoveUser(String mail) throws BusinessException {
		Employee employee = new Employee();
		String message;
		try {
			employee = employeeMySQLRepository.findByEmpMail(mail);
			employeeMySQLRepository.delete(employee);
			userRepository.delete(userRepository.findByUsername(employee.getEmpName()).get());
			message = employee.getEmpName() + " removed succussfully";
			log.debug("Admin-Dao-AdminRemoveUser-Accessed");
		} catch (Exception e) {
			log.error("Exception occured : Employee not found");
			log.error("Admin-Dao-AdminRemoveUser-Failed");
			throw new BusinessException("Employee_Not_Found", null, new String[] { mail });
		}
		return message;
	}

	@Override
	public String adminRemoveAllEmployeeMongoDB() {
		employeeMongoDBRepository.deleteAll();
		log.debug("Admin-Dao-adminRemoveAllUser-Accessed");
		return "All Employees removed from MongoDB";
	}

	@Override
	public MessageDisplayDTO adminUpdateEmployeeDetail(String mail, AdminUpdateEmployeeDTO employee)
			throws BusinessException {
		Employee updatethisEmp = employeeMySQLRepository.findByEmpMail(mail);
		if (updatethisEmp == null) {
			log.error("Admin-Dao-adminRemoveAllUser-Failed");
			return new MessageDisplayDTO("Mail id not found");
		} else {
			updatethisEmp.setEmpPosition(employee.getEmpPosition());
			updatethisEmp.setEmpAchievement(employee.getEmpAchievement());
			log.debug("Admin-Dao-adminRemoveAllUser-Accessed");
		}
		return new MessageDisplayDTO(updatethisEmp.getEmpName() + " updated successsfully");
	}

	@Override
	public List<Employee> adminViewAllEmployee() throws BusinessException {
		log.debug("Admin-Dao-adminViewAllEmployee-Accessed");
		return employeeMySQLRepository.findAll();
	}

	@Override
	public List<Employee> adminViewAllEmployeeMongoDB() throws BusinessException {
		log.debug("Admin-Dao-adminViewAllEmployeeMongoDB-Accessed");
		return employeeMongoDBRepository.findAll();
	}

	@Override
	public GetEmployeeDTO adminGetEmployeeByMailId(String mail) throws BusinessException {
		GetEmployeeDTO empByMailId = new GetEmployeeDTO();
		Employee emp = employeeMySQLRepository.findByEmpMail(mail);
		if (emp == null) {
			empByMailId.setMessage("Employee not found");
			log.error("Admin-Dao-adminGetEmployeeByMailId-Failed");
		} else {
			empByMailId.setEmployeeDtl(emp);
			log.debug("Admin-Dao-adminGetEmployeeByMailId-Accessed");
		}
		return empByMailId;
	}

	@Override
	public GetEmployeeDTO userViewEmployeeDetail(Authentication auth) {
		GetEmployeeDTO respectivEmployeeDtl = new GetEmployeeDTO();
		Employee empDetail = new Employee();
		if (employeeMySQLRepository.findByEmpName(auth.getName()) == null) {
			respectivEmployeeDtl.setMessage("your don't have any content to view. Contact your admin and get username");
			log.error("Admin-Dao-UserUpdateAddressDtl-Failed");
		} else {
			BeanUtils.copyProperties(employeeMySQLRepository.findByEmpName(auth.getName()), empDetail);
			respectivEmployeeDtl.setEmployeeDtl(empDetail);
			log.debug("Admin-Dao-userViewEmployeeDetail-Accessed");
		}
		return respectivEmployeeDtl;
	}

	@Override
	public String userUpdateAddressDtl(Authentication authentication, AddressDTO address) throws BusinessException {

		String message = null;
		try {
			Employee currentEmployee = employeeMySQLRepository.findByEmpName(authentication.getName());
			List<Address> currentEmployeeAddress = new ArrayList<>();
			currentEmployeeAddress.addAll(currentEmployee.getEmployeeAddress());
			for (Address setOldAddress : currentEmployeeAddress) {
				if (setOldAddress.getAddressType().equals(address.getAddressType())) {
					if (address.getDoorNumber() != null)
						setOldAddress.setDoorNumber(address.getDoorNumber());
					if (address.getAddressType() != null)
						setOldAddress.setStreet(address.getStreet());
					if (address.getDistrict() != null)
						setOldAddress.setDistrict(address.getDistrict());
					if (address.getState() != null)
						setOldAddress.setState(address.getState());
					if (address.getPinCode() != null)
						setOldAddress.setPinCode(address.getPinCode());
					currentEmployee.setEmployeeAddress(currentEmployeeAddress);
					employeeMySQLRepository.save(currentEmployee);
					log.debug("Admin-Dao-UserUpdateAddressDtl-Accessed");
					message = "Thanks for updating your old address detail";
				}
			}
			for (Address setNewAddress : currentEmployeeAddress) {
				if (!(setNewAddress.getAddressType().equals(address.getAddressType()))) {
					Address newAddress = new Address();
					BeanUtils.copyProperties(address, newAddress);
					currentEmployeeAddress.add(newAddress);
					currentEmployee.setEmployeeAddress(currentEmployeeAddress);
					employeeMySQLRepository.save(currentEmployee);
					log.debug("Admin-Dao-UserUpdateAddressDtl-Accessed");
					message = "Thanks for updating your new address detail";
				}
			}

		} catch (Exception e) {
			log.error("Admin-Dao-UserUpdateAddressDtl-Failed");
			throw new BusinessException("Error occured while updating address : " + e.getMessage());
		}
		return message;
	}

	@Override
	public MessageDisplayDTO userUpdatePersonDtl(Authentication authentication, PersonDTO person)
			throws BusinessException, TechnicalException {
		Employee currentEmployee = employeeMySQLRepository.findByEmpName(authentication.getName());
		if (person.getEmpMobileNumber() != null) {
			employeeValidator.checkValidMobileNumber(person.getEmpMobileNumber());
			currentEmployee.setEmpMobileNumber(person.getEmpMobileNumber());
		}
		if (person.getEmpAadharNumber() != null) {
			employeeValidator.checkValidAadharNumber(person.getEmpAadharNumber());
			currentEmployee.getEmpPersonalDetail().setEmpAadharNumber(person.getEmpAadharNumber());
		}
		if (person.getEmpAge() != null)
			currentEmployee.getEmpPersonalDetail().setEmpAge(person.getEmpAge());
		if (person.getEmpMaritalStatus() != null)
			currentEmployee.getEmpPersonalDetail().setEmpMaritalStatus(person.getEmpMaritalStatus());
		if (person.getEmpGender() != null)
			currentEmployee.getEmpPersonalDetail().setEmpGender(person.getEmpGender());
		employeeMySQLRepository.save(currentEmployee);
		log.debug("Admin-Dao-userUpdatePersonDtl-Accessed");
		return new MessageDisplayDTO("Thanks for updating your personal detail");
	}

}
