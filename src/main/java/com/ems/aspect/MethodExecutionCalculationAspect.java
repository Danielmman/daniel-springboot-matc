package com.ems.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Aspect
@Configuration
public class MethodExecutionCalculationAspect {

	@Around("com.ems.aspect.CommonJoinPointConfig.trackTimeAnnotation()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		Object returnProceed = joinPoint.proceed();

		long timeTaken = System.currentTimeMillis() - startTime;
		log.info("Time Taken by {} is {}", joinPoint, timeTaken);

		return returnProceed;
	}
}
