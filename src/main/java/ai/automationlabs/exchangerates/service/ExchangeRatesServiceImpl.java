package ai.automationlabs.exchangerates.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.automationlabs.exchangerates.dao.ExchangeRatesDao;
import ai.automationlabs.exchangerates.domain.CurrenciesListResponse;
import ai.automationlabs.exchangerates.domain.ExchangeRatesResponse;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

	private static final String BASE_CURRENCY = "USD";

	private ExchangeRatesDao exchangeRatesDao;

	private Map<String, String> currenciesCache;

	private Map<String, Double> exchangeRatesCache;

	private Map<String, ExchangeRatesResponse> currencyCode2exchangeRates = new HashMap<>();

	@Autowired
	public ExchangeRatesServiceImpl(ExchangeRatesDao exchangeRatesDao) {
		this.exchangeRatesDao = exchangeRatesDao;
	}

	@PostConstruct
	private void init() {
		refreshData();
	}

	@Override
	public synchronized void refreshData() {
		exchangeRatesCache = Collections.unmodifiableMap(exchangeRatesDao.getExchangeRates());
		currenciesCache = Collections.unmodifiableMap(exchangeRatesDao.getCurrencies());
		currencyCode2exchangeRates = new HashMap<>();
		currencyCode2exchangeRates.put(BASE_CURRENCY, createExchangeRatesResponse(exchangeRatesCache));
	}

	@Override
	public CurrenciesListResponse getCurrenciesList() {
		CurrenciesListResponse response = new CurrenciesListResponse();
		response.setCurrencyCodes(currenciesCache.keySet());
		response.setSuccess(true);
		return response;
	}

	@Override
	public synchronized ExchangeRatesResponse getExchangeRates(String currency) {
		if (!currencyCode2exchangeRates.containsKey(currency)) {
			currencyCode2exchangeRates.put(currency, computeExchangeRates(currency));
		}
		return currencyCode2exchangeRates.get(currency);
	}

	// a relatively naive way to compute exchange rates
	// by using exchange rate to the base currency
	private ExchangeRatesResponse computeExchangeRates(String currency) {
		Map<String, Double> exchangeRates = new TreeMap<>();
		Double exchangeRateBaseCurrency = exchangeRatesCache.get(currency);
		for (Map.Entry<String, Double> entry : exchangeRatesCache.entrySet()) {
			double rate = currency.equals(entry.getKey()) ? 1 : entry.getValue() / exchangeRateBaseCurrency;
			exchangeRates.put(entry.getKey(), rate);
		}
		return createExchangeRatesResponse(exchangeRates);
	}

	private ExchangeRatesResponse createExchangeRatesResponse(Map<String, Double> exchangeRates) {
		ExchangeRatesResponse response = new ExchangeRatesResponse();
		for (Map.Entry<String, Double> entry : exchangeRates.entrySet()) {
			String code = entry.getKey();
			String name = currenciesCache.get(code);
			response.addExchangeRate(code, name, entry.getValue());
		}
		response.setSuccess(true);
		return response;
	}
	
	
}
