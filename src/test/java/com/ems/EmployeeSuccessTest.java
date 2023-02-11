package com.ems;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ems.base.response.Response;
import com.ems.customdt.Achievement;
import com.ems.customdt.AddressType;
import com.ems.customdt.EmployeePosition;
import com.ems.customdt.Gender;
import com.ems.customdt.MaritalStatus;
import com.ems.dto.AdminUpdateEmployeeDTO;
import com.ems.dto.LoginRequestDTO;
import com.ems.dto.MessageDisplayDTO;
import com.ems.model.Address;
import com.ems.model.Employee;
import com.ems.model.Person;
import com.ems.model.sub.User;

import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;

@Log4j2
@TestMethodOrder(OrderAnnotation.class)
class EmployeeSuccessTest {

	static String adminToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MTE3MTA1NCwiZXhwIjoxNjkyNzI4NjU0fQ.v-uMOu343ZHZt-PjDVlr_CTnrXvz7qSwF4dXgmMrXD5lR5-zl2SxnRbv0GVxYD8vZPj5WQ6AGMCecB8aBaZWZg";

	static String empToken;

	static String globalUserName;

	static String globalPassword;

	static String empMailId;

	@Test
	@Order(1)
	void signupUser() {
		RestTemplate restTemplate = new RestTemplate();
		User user = new User();
		user.setPassword(RandomString.make(8));
		user.setUsername(RandomString.make(5));
		globalUserName = user.getUsername();
		globalPassword = user.getPassword();
		final String baseUrl = "http://localhost:3030/v1/auth/signup";
		ResponseEntity<?> result = restTemplate.postForEntity(baseUrl, user, MessageDisplayDTO.class);
		log.debug("Result Body : " + result.getBody());
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	@Order(2)
	void signinUser() {
		RestTemplate restTemplate = new RestTemplate();
		LoginRequestDTO user = new LoginRequestDTO();
		user.setPassword(globalPassword);
		user.setUsername(globalUserName);
		final String baseUrl = "http://localhost:3030/v1/auth/signin";
		ResponseEntity<?> result = restTemplate.postForEntity(baseUrl, user, Response.class);
		Response<?> response = (Response<?>) result.getBody();
		log.debug(response);
		String jwtResponse = response.getData().toString();
		Integer index = jwtResponse.indexOf("accessToken=");
		jwtResponse = jwtResponse.substring(index + 12);
		Integer lastIndex = jwtResponse.indexOf(",");
		empToken = jwtResponse.substring(0, lastIndex);
		log.debug("Employee Token : " + empToken);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	@Order(3)
	void testAdminSaveEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = new Employee();
		emp.setEmpId(RandomString.make());
		emp.setEmpJoiningDate(LocalDate.now());
		emp.setEmpName(globalUserName);
		emp.setEmpMail(globalUserName + "@ems.com");
		empMailId = emp.getEmpMail();
		emp.setEmpMobileNumber("9977665544");
		emp.setEmpPosition(EmployeePosition.PROGRAMMER_ANALYST);
		emp.setEmpPersonalDetail(
				new Person(RandomString.make(), Gender.FEMALE, 22, MaritalStatus.UNMARRIED, "998877665544"));
		List<Address> empAddresse = new ArrayList<>();
		Address tempAddress = new Address(RandomString.make(), AddressType.CURRENT, "15/3", "KK Nagar", "Trichy",
				"Tamil Nadu", 620012);
		empAddresse.add(tempAddress);
		emp.setEmployeeAddress(empAddresse);
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		HttpEntity<?> request = new HttpEntity<Object>(emp, headers);
		final String baseUrl = "http://localhost:3030/v1/admin/employee";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.POST, request, Response.class);
		log.debug("Result Body : " + result.getBody());
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	@Order(4)
	void testEmployeeGetDetailsByAuth() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(empToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/employee";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		log.debug("Result Body : " + result.getBody());
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	@Order(5)
	void testEmpUpdateAddressDetailByAuth() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(empToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/employee/address?addressType=CURRENT&doorNumber=12/24&street=RMS Colony 2nd street&district=Trichy&state=TN&pinCode=620012";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Response.class);
		log.debug("Result Body : " + result.getBody());
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	@Order(6)
	void testEmpUpdatePersonDetailByAuth() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(empToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/employee/person?empMobileNumber=8877665544&gender=MALE&district=Trichy&state=TN";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Response.class);
		log.debug("Result Body : " + result.getBody());
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	@Order(7)
	void testAdminGetAllEmployeeByPagination() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/?pageSize=10&sortBy=empName";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		log.debug("Result Body : " + result.getBody());
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	@Order(8)
	void testAdminUpdateEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		AdminUpdateEmployeeDTO updatedetail = new AdminUpdateEmployeeDTO();
		updatedetail.setEmpPosition(EmployeePosition.SENIOR_DIRECTOR);
		updatedetail.setEmpAchievement(Achievement.LEVEL_4);
		HttpEntity<?> request = new HttpEntity<Object>(updatedetail, headers);
		final String baseUrl = "http://localhost:3030/v1/admin/employee?mail=" + globalUserName + "@ems.com";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Response.class);
		log.debug("Result Body : " + result.getBody());
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	@Order(9)
	void testAdminGetAllEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/admin/employee/all";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		log.debug("Result Body : " + result.getBody());
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	@Order(10)
	void testAdminGetEmployeeByMailId() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/admin/employee?mail=" + globalUserName + "@ems.com";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		log.debug("Result Body : " + result.getBody());
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	@Order(11)
	void testAdminDeleteEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);

		final String baseUrl = "http://localhost:3030/v1/admin/employee?mail=" + empMailId;
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.DELETE, request, Response.class);
		log.debug("Result Body : " + result.getBody());
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

}
