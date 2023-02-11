package com.ems;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ems.base.response.Response;
import com.ems.customdt.Achievement;
import com.ems.customdt.AddressType;
import com.ems.customdt.EmployeePosition;
import com.ems.customdt.Gender;
import com.ems.customdt.MaritalStatus;
import com.ems.dto.AddressDTO;
import com.ems.dto.AdminUpdateEmployeeDTO;
import com.ems.dto.JwtResponseDTO;
import com.ems.dto.LoginRequestDTO;
import com.ems.dto.MessageDisplayDTO;
import com.ems.dto.PersonDTO;
import com.ems.dto.SignupRequestDTO;
import com.ems.model.Address;
import com.ems.model.Employee;
import com.ems.model.Person;

import net.bytebuddy.utility.RandomString;

class EmployeeFailTest {

	String adminToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MTE3MTA1NCwiZXhwIjoxNjkyNzI4NjU0fQ.v-uMOu343ZHZt-PjDVlr_CTnrXvz7qSwF4dXgmMrXD5lR5-zl2SxnRbv0GVxYD8vZPj5WQ6AGMCecB8aBaZWZg";

	String empToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEYW5pZWwiLCJpYXQiOjE2NjAwMzY5ODgsImV4cCI6MTY2MTAzNjk4OH0.h2_vT-TjId61X7SFJacTOS5Pr2PM06yguIWU6ak2O9kTr9AjAMk7yDtbBz4b3Sb2ITbUA7RIHV7HPYWWxPe-dA";

	@Test
	void failSignupUser() {
		RestTemplate restTemplate = new RestTemplate();
		SignupRequestDTO user = new SignupRequestDTO();
		user.setPassword(RandomString.make(5)); // Must minimum value meets requirement (6)
		user.setUsername(RandomString.make(5));
		final String baseUrl = "http://localhost:3030/v1/auth/signup";
		try {
			restTemplate.postForEntity(baseUrl, user, MessageDisplayDTO.class);
		} catch (HttpStatusCodeException ex) {
			Assert.assertEquals("400 BAD_REQUEST", ex.getStatusCode().toString());
		}
	}

	@Test
	void failSigninUser() {
		RestTemplate restTemplate = new RestTemplate();
		LoginRequestDTO user = new LoginRequestDTO();
		user.setPassword("123456");
		user.setUsername("Anonymous");
		final String baseUrl = "http://localhost:3030/v1/auth/signin";
		try {
			restTemplate.postForEntity(baseUrl, user, JwtResponseDTO.class);
		} catch (HttpStatusCodeException ex) {
			Assert.assertEquals("401 UNAUTHORIZED", ex.getStatusCode().toString());
		}
	}

	@Test
	void failAdminGetAllEmployeeByPagination() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/?pageSize=10&sortBy=customSort";
		try {
			restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		} catch (HttpStatusCodeException e) {
			Assert.assertEquals("500 INTERNAL_SERVER_ERROR", e.getStatusCode().toString());
		}
	}

	@Test
	void failAdminSaveEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = new Employee();
		emp.setEmpId(RandomString.make());
		emp.setEmpJoiningDate(LocalDate.now());
		emp.setEmpName(RandomString.make(5));
		emp.setEmpMail(emp.getEmpName() + "@ems.com");
		emp.setEmpMobileNumber("9977"); // Mobile number not valid
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
		try {
			restTemplate.exchange(baseUrl, HttpMethod.POST, request, Response.class);
		} catch (HttpStatusCodeException e) {
			Assert.assertEquals("500 INTERNAL_SERVER_ERROR", e.getStatusCode().toString());
		}
	}

	@Test
	void failAdminDeleteEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/admin/employee?mail=1e4h4@ems.com";
		try {
			restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Response.class); // HttpMethod changed
		} catch (HttpStatusCodeException e) {
			Assert.assertEquals("400 BAD_REQUEST", e.getStatusCode().toString());
		}
	}

	@Test
	void failAdminUpdateEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		AdminUpdateEmployeeDTO updatedetail = new AdminUpdateEmployeeDTO();
		updatedetail.setEmpPosition(EmployeePosition.SENIOR_DIRECTOR);
		updatedetail.setEmpAchievement(Achievement.LEVEL_3);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/admin/employee?mail=e4zXs@ems.com";
		try {
			restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Response.class); // HttpMethod changed
		} catch (HttpStatusCodeException e) {
			Assert.assertEquals("400 BAD_REQUEST", e.getStatusCode().toString());
		}
	}

	@Test
	void failAdminGetAllEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/admin/employee/all";
		try {
			restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Response.class); // Wrong method
		} catch (HttpStatusCodeException e) {
			Assert.assertEquals("405 METHOD_NOT_ALLOWED", e.getStatusCode().toString());
		}
	}

	@Test
	void failAdminGetEmployeeByMailId() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(adminToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/employee?mail=gdff@ems.com"; // wrong URL
		try {
			restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		} catch (HttpStatusCodeException e) {
			Assert.assertEquals("403 FORBIDDEN", e.getStatusCode().toString());
		}
	}

	@Test
	void failEmployeeGetDetailsByAuth() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("Invalid Token"); // Invalid Token
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:3030/v1/employee";
		try {
			restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		} catch (HttpStatusCodeException e) {
			Assert.assertEquals("401 UNAUTHORIZED", e.getStatusCode().toString());
		}
	}

	@Test
	void failEmpUpdateAddressDetailByAuth() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(empToken); // Different Authentication
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAddressType(AddressType.PERMANENT);
		addressDTO.setDistrict("Chennai");
		addressDTO.setDoorNumber("12/24");
		addressDTO.setPinCode(620012);
		addressDTO.setState("TN");
		addressDTO.setStreet("Simco Colony");
		HttpEntity<?> request = new HttpEntity<Object>(addressDTO, headers);
		final String baseUrl = "http://localhost:3030/v1/employee/address?addressType=CURRENT&doorNumber=12/24&street=RMS Colony 2nd street&district=Trichy&state=TN&pinCode=620012";
		try {
			restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Response.class);
		} catch (HttpStatusCodeException e) {
			Assert.assertEquals(401, e.getRawStatusCode());
		}
	}

	@Test
	void failEmpUpdatePersonDetailByAuth() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(empToken);
		PersonDTO personDTO = new PersonDTO();
		HttpEntity<?> request = new HttpEntity<Object>(personDTO, headers);
		final String baseUrl = "http://localhost:3030/v1/employee/person?empMobileNumber=8877665544&gender=NOTGIVEN&district=Trichy&state=TN"; // Gender
																																				// value
																																				// wrong
		try {
			restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Response.class);
		} catch (HttpStatusCodeException e) {
			Assert.assertEquals(401, e.getRawStatusCode());
		}
	}
}
