package com.crossover.exchangerates.exception;

import org.apache.commons.lang3.StringUtils;

public class CurrencyServerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CurrencyServerException(String errorMessage) {
		super(errorMessage);
		if (StringUtils.isBlank(errorMessage)) {
			throw new IllegalArgumentException("ErrorMessage should not be blank!");
		}
	}
}
