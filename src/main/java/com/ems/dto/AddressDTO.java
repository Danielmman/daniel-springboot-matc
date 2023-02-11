package com.ems.dto;

import com.ems.customdt.AddressType;

import lombok.Data;

@Data
public class AddressDTO {
	private AddressType addressType;
	private String doorNumber;
	private String street;
	private String district;
	private String state;
	private Integer pinCode;
}
