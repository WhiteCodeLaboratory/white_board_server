package com.whitecodelab.whiteboard.common.exception;

import com.whitecodelab.whiteboard.common.code.APICode;

/**************************************************************
 * ClassName: APIException.java
 * Package  : com.kscc.mobilecall.common.exception
 * Comment  : API 공통 Exception 클래스.
 * @version : 1.0
 * @author  : 유라이프 이상섭
 * @date    : 2014. 6. 18.
**************************************************************/
@SuppressWarnings("serial")
public class APIException extends RuntimeException {
	
	private APICode apiCode;
	private Object tagObject;
	
	@SuppressWarnings("static-access")
	public APIException() {
		this.apiCode = apiCode.FAIL;
	}

	public APIException(APICode apiCode) {
		super(apiCode.name());
		this.apiCode = apiCode;
	}
	
	public APIException(APICode apiCode, String message, Throwable cause) {
		super(message, cause);
		this.apiCode = apiCode;
	}

	public APIException(APICode apiCode, String message) {
		super(message);
		this.apiCode = apiCode;
	}

	public APIException(APICode apiCode, Throwable cause) {
		super(cause);
		this.apiCode = apiCode;
	}
	
	public APIException(APICode apiCode, Object tagObject) {
		super(apiCode.name());
		this.apiCode = apiCode;
		this.tagObject = tagObject;
	}
	
	public APIException(APICode apiCode, String message, Object tagObject) {
		super(message);
		this.apiCode = apiCode;
		this.tagObject = tagObject;
	}
	
	public APICode getAPICode() {
		return apiCode;
	}
	
	public void setAPICode(APICode apiCode) {
		this.apiCode = apiCode;
	}

	public Object getTagObject() {
		return this.tagObject;
	}

	public void setTagObject(Object tagObject) {
		this.tagObject = tagObject;
	}
	
}
