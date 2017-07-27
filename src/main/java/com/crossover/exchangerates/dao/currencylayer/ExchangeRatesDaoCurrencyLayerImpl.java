package com.crossover.exchangerates.dao.currencylayer;

import static java.lang.String.format;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.crossover.exchangerates.dao.ExchangeRatesDao;
import com.crossover.exchangerates.domain.currencylayer.BaseResponse;
import com.crossover.exchangerates.domain.currencylayer.ListResponse;
import com.crossover.exchangerates.domain.currencylayer.LiveResponse;
import com.crossover.exchangerates.exception.CurrencyServerException;

@Repository
public class ExchangeRatesDaoCurrencyLayerImpl implements ExchangeRatesDao {
	private String currencyLayerURLTemplate;
	
        // package-private to facilitate unit testing
	RestTemplate restTemplate = new RestTemplate();

	@Autowired
	public ExchangeRatesDaoCurrencyLayerImpl(@Value("${currencylayer.access.key}") String currencyLayerAccessKey) {
		this.currencyLayerURLTemplate = "http://apilayer.net/api/%s?access_key=" + currencyLayerAccessKey;
	}

	@Override
	public Map<String, Double> getExchangeRates() {
		String url = format(currencyLayerURLTemplate, "live");
		LiveResponse response = callCurrencyLayer(url, LiveResponse.class);
		Map<String, Double> quotes = response.getQuotes();
		Map<String, Double> exchangeRates = new TreeMap<String, Double>();
		if (quotes != null) {
			for (Map.Entry<String, Double> entry : quotes.entrySet()) {
				exchangeRates.put(entry.getKey().substring(3), entry.getValue());
			}
		}
		return exchangeRates;
	}

	@Override
	public Map<String, String> getCurrencies() {
		String url = format(currencyLayerURLTemplate, "list");
		ListResponse response = callCurrencyLayer(url, ListResponse.class);
		return response.getCurrencies() != null ? new TreeMap<>(response.getCurrencies()) : new TreeMap<String, String>();
	}

	private <T extends BaseResponse> T callCurrencyLayer(String url, Class<T> responseClass) {
		T response = restTemplate.getForObject(url, responseClass);
		if (!response.isSuccess()) {
			throw new CurrencyServerException(response.getError().getInfo());
		}
		return response;
	}

}
