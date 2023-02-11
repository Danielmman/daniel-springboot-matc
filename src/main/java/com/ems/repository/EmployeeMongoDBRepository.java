package com.ems.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.ems.model.Employee;

@EnableMongoRepositories
public interface EmployeeMongoDBRepository extends MongoRepository<Employee, String> {

}
