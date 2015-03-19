package com.whitecodelab.whiteboard.common.context;

import com.whitecodelab.whiteboard.common.util.EmptyUtil;

public class LogContext {

	/** 서비스명 */
	public static String SERVICE_NAME = "SmartCallService";

	/** 성공 */
	public static String RESULT_TYPE_SUCC = "SUCC";
	/** 실 */
	public static String RESULT_TYPE_FAIL = "FAIL";

	private String key = "";
	private String resultType = "";
	private String resultCode = "";
	private long totalTime = 0;
	private String resultMsg = "";
	private String callType = "";
	private String callUrl = "";
	private String sessionId = "";

	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getCallUrl() {
		return callUrl;
	}
	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	public String getResultMsg() {
		return EmptyUtil.isNotStringEmpty(resultMsg)?"MSG=[" + resultMsg + "]" : resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getSessionId() {
		return EmptyUtil.isNotStringEmpty(sessionId)?sessionId:"";
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getOmcLog(){

		StringBuffer strLog = new StringBuffer();
		String gubun = ",";
//		strLog.append(startTime).append(gubun); 	// 로그시간정보 - yyyymmddhhmmss
		strLog.append("[OMC] - "); 	// 서비스명
		strLog.append(SERVICE_NAME).append(gubun); 	// 서비스명
		strLog.append(callUrl).append(gubun); 		// 호출URL
		strLog.append(getSessionId()).append(gubun); 	// Session ID
		strLog.append(totalTime).append(gubun); 	// 응답(처리)시간 - msec
		strLog.append(resultCode).append(gubun);	// 응답코드
		strLog.append(resultType).append(gubun);	// SUCC, FAIL
		strLog.append(key).append(gubun); 			// 중요데이터 정보 구분자 |
		strLog.append(getResultMsg()); 				// 처리결과 메세지

		return strLog.toString();
	}

}