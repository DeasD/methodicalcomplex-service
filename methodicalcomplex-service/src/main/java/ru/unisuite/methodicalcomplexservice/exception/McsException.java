package ru.unisuite.methodicalcomplexservice.exception;

public class McsException extends Exception {

	/**
	 * 
	 */

	private static final long serialVersionUID = 6916645169327058784L;

	public McsException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public McsException(String errorMessage) {
		super(errorMessage);
	}

}
