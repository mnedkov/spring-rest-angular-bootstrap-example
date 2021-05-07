package eu.automationlabs.exchangerates.service;

import eu.automationlabs.exchangerates.domain.CurrenciesListResponse;
import eu.automationlabs.exchangerates.domain.ExchangeRatesResponse;

public interface ExchangeRatesService {

	void refreshData();

	CurrenciesListResponse getCurrenciesList();

	ExchangeRatesResponse getExchangeRates(String currency);

}