package ru.unisuite.methodicalcomplexservice.exception;

public class MethodicalComplexServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6916645169327058784L;
	private int errorCode;

	public MethodicalComplexServiceException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	public MethodicalComplexServiceException(String errorMessage) {
		this(0, errorMessage);
	}

	public MethodicalComplexServiceException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public int getErrorCode() {
		return errorCode;
	}

}
