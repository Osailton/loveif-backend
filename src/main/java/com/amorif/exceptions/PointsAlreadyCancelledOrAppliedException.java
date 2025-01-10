package com.amorif.exceptions;

public class PointsAlreadyCancelledOrAppliedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PointsAlreadyCancelledOrAppliedException(String msg) {
		super(msg);
	}
}
