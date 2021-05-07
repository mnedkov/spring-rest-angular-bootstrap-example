package ai.automationlabs.exchangerates.service;

import ai.automationlabs.exchangerates.domain.CurrenciesListResponse;
import ai.automationlabs.exchangerates.domain.ExchangeRatesResponse;

public interface ExchangeRatesService {

	void refreshData();

	CurrenciesListResponse getCurrenciesList();

	ExchangeRatesResponse getExchangeRates(String currency);

}