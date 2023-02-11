package com.ems.repository;

import javax.persistence.Id;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.model.sub.LoginLog;

public interface LoginLogRepository extends JpaRepository<LoginLog, Id> {

}
