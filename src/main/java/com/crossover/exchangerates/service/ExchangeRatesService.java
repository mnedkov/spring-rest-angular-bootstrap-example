package com.crossover.exchangerates.service;

import com.crossover.exchangerates.domain.CurrenciesListResponse;
import com.crossover.exchangerates.domain.ExchangeRatesResponse;

public interface ExchangeRatesService {

	void refreshData();

	CurrenciesListResponse getCurrenciesList();

	ExchangeRatesResponse getExchangeRates(String currency);

}