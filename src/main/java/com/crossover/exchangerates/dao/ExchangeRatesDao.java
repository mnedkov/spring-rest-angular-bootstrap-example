package com.crossover.exchangerates.dao;

import java.util.Map;

public interface ExchangeRatesDao {
	Map<String, Double> getExchangeRates();

	Map<String, String> getCurrencies();

}
