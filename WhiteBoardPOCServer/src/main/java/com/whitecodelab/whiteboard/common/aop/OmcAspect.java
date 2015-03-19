package com.whitecodelab.whiteboard.common.aop;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.whitecodelab.whiteboard.common.code.APICode;
import com.whitecodelab.whiteboard.common.context.LogContext;
import com.whitecodelab.whiteboard.common.exception.APIException;

@Component
@Aspect
@Order(1)
public class OmcAspect {

	/** Log **/
	private static final Logger logger = LoggerFactory.getLogger("OMC_LOG");
	
	@Around("execution(* com.whitecodelab.whiteboard.*.*.*Controller.*(..))")
	public Object makeOmcLog(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object retVal = null;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		LogContext logContext = new LogContext();
		logContext.setCallUrl(request.getServletPath());
		logContext.setResultCode(APICode.SUCCESS.getCode());
		logContext.setResultType(LogContext.RESULT_TYPE_SUCC);
		logContext.setSessionId(request.getRequestedSessionId());
		logContext.setKey(searchPointKeySetting(joinPoint.getArgs())); 	//중요키값 세팅.
		StopWatch stopWatch = new StopWatch();  		// 응답 시간 측정
		
		try {
			stopWatch.start(joinPoint.toShortString()); 	// 응답시간 시작
			// start stopwatch
			retVal = joinPoint.proceed(joinPoint.getArgs());
		} catch(APIException ae) {
			logContext.setResultMsg(ae.getAPICode().name() + " : " + ae.getMessage());
			logContext.setResultCode(ae.getAPICode().getCode());
			logContext.setResultType(LogContext.RESULT_TYPE_FAIL);
			throw ae;
		} catch(Exception e) {
			if (e.getCause() != null && "".equals(e.getCause())) {
				logContext.setResultMsg(e.getCause().toString());
			}
			logContext.setResultCode(APICode.FAIL.getCode());
			logContext.setResultType(LogContext.RESULT_TYPE_FAIL);
			throw e;
		} finally {
			// stop stopwatch
			stopWatch.stop();	 // 응답시간 종료
			logContext.setTotalTime(stopWatch.getTotalTimeMillis());
			String omcLogStr = logContext.getOmcLog();
			logger.info(omcLogStr);
		}
		return retVal;
	}

	@SuppressWarnings("unchecked")
	private String searchPointKeySetting(Object[] objects) {
		StringBuffer sb = new StringBuffer();
		for (Object object : objects) {
			if(object instanceof LinkedHashMap) {
				LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) object;
				for (String parameterKey : map.keySet()) {
					getPointKeyValue(map, parameterKey, sb);
				}
			}
		}
		return sb.toString();
	}
	
	private String getPointKeyValue(LinkedHashMap<String, Object> parameterMap, String parameterKey, StringBuffer sb){
		
		switch (parameterKey) {
		// 회원번호
		case "memNo":
		case "memberNo":
			logAppend("memberNo", parameterMap.get(parameterKey), sb);
			break; 
		case "admNo":
		case "admMemberNo":
			logAppend("admMemberNo", parameterMap.get(parameterKey), sb);
			break;
		default:
			break;
		}
		
		return sb.toString();
	}
	
	private void logAppend(String logKey, Object logValue, StringBuffer sb){
		String logStrGubun = "|";
		if(sb.length() != 0){
			sb.append(logStrGubun);
		}
		sb.append(logKey + "=" + logValue);
	}
}
