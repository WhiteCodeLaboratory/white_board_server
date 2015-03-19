package com.whitecodelab.whiteboard.common.code;


/**************************************************************
 * ClassName: APICode.java
 * Package  : com.kscc.mobilecall.common.code
 * Comment  : Log 코드 enum 클래스
 * @version : 1.0
 * @author  : 유라이프 이상섭
 * @date    : 2014. 6. 18.
**************************************************************/
public enum APICode {
	
	/**
	 * TODO : 성공코드의 경우 S로 시작, 실패 코드의 경우 F로 시작하며 국가별 메세지 처리를 해야하기 때문에
	 * message property에 해당 코드와 매핑이 되는 문구를 등록해야함. 
	 */
	// 공통 응답 코드 - 성공
	SUCCESS												("S0000"),
	// 공통 응답 코드 - 실패
	FAIL												("F0000"),
	
	FAIL_TEST_SELECT									("FT001"),
	FAIL_TEST_DELETE									("FT002"),
	FAIL_TEST_UPDATE									("FT003"),
	FAIL_TEST_INSERT									("FT004"),
	;
	
	private String errorCode;
	
	APICode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getCode() {
		return errorCode;
	}
	
	@Override
	public String toString() {
		return "[" + errorCode + "]";
	}
	
}