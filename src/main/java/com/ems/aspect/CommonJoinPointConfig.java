package com.ems.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	@Pointcut("@annotation(com.ems.aspect.TraceExecuteTime)")
	public void trackTimeAnnotation() {
	}
}
