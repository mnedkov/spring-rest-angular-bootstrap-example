package eu.automationlabs.exchangerates.domain;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesResponse extends BaseResponse {
	private List<ExchangeRate> exchangeRates = new ArrayList<>();

	public List<ExchangeRate> getExchangeRates() {
		return exchangeRates;
	}

	public void addExchangeRate(String code, String name, Double rate) {
		exchangeRates.add(new ExchangeRate(code, name, rate));
	}

	public static class ExchangeRate {
		private String currencyCode;
		private String currencyName;
		private Double rate;

		public ExchangeRate(String currencyCode, String currencyName, Double rate) {
			this.currencyCode = currencyCode;
			this.currencyName = currencyName;
			this.rate = rate;
		}

		public String getCurrencyCode() {
			return currencyCode;
		}

		public String getCurrencyName() {
			return currencyName;
		}

		public Double getRate() {
			return rate;
		}

		@Override
		public String toString() {
			return "ExchangeRate [currencyCode=" + currencyCode + ", currencyName=" + currencyName + ", rate=" + rate + "]";
		}

	}
}
