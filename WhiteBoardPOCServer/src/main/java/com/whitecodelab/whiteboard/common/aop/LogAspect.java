package com.whitecodelab.whiteboard.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(0)
public class LogAspect extends AbstractLoggingAspect {

	/* TODO : AOP로 컨트롤러 로그를 관리.
	 */
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Around("execution(* com.whitecodelab.whiteboard.*.*.*Controller.*(..))")
	public Object loggingMessageConverter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String logHeader = getLogHeader(proceedingJoinPoint);
		loggingBeginAndArgs(proceedingJoinPoint, logHeader);
		
		Object returnObject = proceedingJoinPoint.proceed();
		
		loggingResponseAndEnd(logHeader, returnObject);
		return returnObject;
	}
	
	@Override
	protected Logger getLogger() {
		return logger;
	}
	
}