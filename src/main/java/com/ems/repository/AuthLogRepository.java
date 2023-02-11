package com.ems.repository;

import javax.persistence.Id;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.model.sub.AuthenticationLog;

public interface AuthLogRepository extends JpaRepository<AuthenticationLog, Id> {

}
