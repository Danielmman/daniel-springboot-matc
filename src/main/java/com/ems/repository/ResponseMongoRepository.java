package com.ems.repository;

import javax.persistence.Id;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.ems.model.sub.UniqueResponse;

@EnableMongoRepositories
public interface ResponseMongoRepository extends MongoRepository<UniqueResponse, Id> {

}
