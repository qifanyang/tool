package com.utils;

/**
 * 断言异常,逻辑线程应捕获该异常
 */
public class AssertException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AssertException() {
		super();
	}

	public AssertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AssertException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssertException(String message) {
		super(message);
	}

	public AssertException(Throwable cause) {
		super(cause);
	}

	
}
