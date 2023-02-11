package com.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author Daniel
 *
 */
@Log4j2
@SpringBootApplication
public class EMSApp {

	/**
	 * Employee Management System - EMS Application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EMSApp.class, args);
		log.info("Employee Management System Application Started");
	}
}
