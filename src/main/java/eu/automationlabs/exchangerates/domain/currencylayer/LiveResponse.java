package eu.automationlabs.exchangerates.domain.currencylayer;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveResponse extends BaseResponse {
	private String source;
	private Map<String, Double> quotes;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Map<String, Double> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<String, Double> quotes) {
		this.quotes = quotes;
	}
}
