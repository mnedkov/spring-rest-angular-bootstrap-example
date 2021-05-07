package ai.automationlabs.exchangerates.domain;

import java.util.Set;

public class CurrenciesListResponse extends BaseResponse{
	private Set<String> currencyCodes;

	public Set<String> getCurrencyCodes() {
		return currencyCodes;
	}

	public void setCurrencyCodes(Set<String> currencyCodes) {
		this.currencyCodes = currencyCodes;
	}
}
