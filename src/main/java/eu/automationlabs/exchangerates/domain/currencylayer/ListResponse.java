package eu.automationlabs.exchangerates.domain.currencylayer;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResponse extends BaseResponse {

	private Map<String, String> currencies;

	public Map<String, String> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(Map<String, String> currencies) {
		this.currencies = currencies;
	}

}
