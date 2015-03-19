package com.whitecodelab.whiteboard.common.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**************************************************************
 * ClassName: AbstractLoggingAspect.java
 * Package  : com.kscc.mobilecall.common.aop
 * Comment  : 로그 AOP 추상클래스
 * @version : 1.0
 * @author  : 유라이프 이상섭
 * @date    : 2014. 6. 18.
**************************************************************/
public abstract class AbstractLoggingAspect {
	
	/**************************************************************
	 * MethodName: loggingBeginAndArgs
	 * @author   : 유라이프 이상섭
	 * @date     : 2014. 6. 18.
	 * Comment   : 클라이언트로 부터 전달되는 파라미터의 정보를 로그로 기록.
	**************************************************************/
	protected void loggingBeginAndArgs(ProceedingJoinPoint proceedingJoinPoint, String headerLog) {
		getLogger().info(headerLog + "begin");
		for (int i = 0; i < proceedingJoinPoint.getArgs().length; i++) {
			Object arg = proceedingJoinPoint.getArgs()[i];
			if (arg instanceof HttpServletRequest) {
				continue;
			}
			getLogger().info(headerLog + "arg(" + i + ") " + arg);
		}
	}

	/**************************************************************
	 * MethodName: loggingResponseAndEnd
	 * @author   : 유라이프 이상섭
	 * @date     : 2014. 6. 18.
	 * Comment   : 요청의 결과값을 로그로 기록.
	**************************************************************/
	protected void loggingResponseAndEnd(String headerLog, Object returnObject) {
		if(returnObject instanceof ModelAndView){
			getLogger().info(headerLog + "resultLog " + resultLog((ModelAndView)returnObject));
		} else {
			getLogger().info(headerLog + "resultLog " + resultLogJson(returnObject));
		}
		loggingEnd(headerLog);
	}
	
	private String resultLog(ModelAndView modelandView){
		if (modelandView == null) {
			return "";
		} else
			return resultLogJson(modelandView.getModel());
	}
	
	private String resultLogJson(Object object){
		if (object == null) {
			return "";
		}
		return new JSONPObject(null, object).toString();
	}
	
	/**************************************************************
	 * MethodName: loggingEnd
	 * @author   : 유라이프 이상섭
	 * @date     : 2014. 6. 18.
	 * Comment   : 로그 끝 기록.
	**************************************************************/
	protected void loggingEnd(String headerLog) {
		getLogger().info(headerLog + "end");
	}

	/**************************************************************
	 * MethodName: getLogHeader
	 * @author   : 유라이프 이상섭
	 * @date     : 2014. 6. 18.
	 * Comment   : 로그 헤더정보 기록.
	**************************************************************/
	protected String getLogHeader(ProceedingJoinPoint proceedingJoinPoint) {
		String thisClassName = proceedingJoinPoint.getTarget().getClass().getSimpleName();
		String callMethod = proceedingJoinPoint.getSignature().getName();

		HttpServletRequest httpServletRequest = null;
		for (Object arg : proceedingJoinPoint.getArgs()) {
			if (arg instanceof HttpServletRequest) {
				httpServletRequest =  ((HttpServletRequest)arg);
			}
		}

		String sessionId = "UNKNOWN-SESSION";
		String requestUri = "UNKNOW_URI";
		if (httpServletRequest != null) {
			requestUri = httpServletRequest.getRequestURI();
			sessionId = httpServletRequest.getSession().getId();
			return "[" + sessionId + "] [" + requestUri + "] [" + thisClassName + "." + callMethod + "] ";
		} else {
			return "[" + thisClassName + "." + callMethod + "] ";
		}
	}

	protected abstract Logger getLogger();
}
