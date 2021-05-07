package eu.automationlabs.exchangerates.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.automationlabs.exchangerates.domain.BaseResponse;
import eu.automationlabs.exchangerates.domain.CurrenciesListResponse;
import eu.automationlabs.exchangerates.domain.ExchangeRatesResponse;
import eu.automationlabs.exchangerates.service.ExchangeRatesService;

@RestController
public class ExchangeRatesController {
	private static final Logger LOG = Logger.getLogger(ExchangeRatesController.class);
	
	private ExchangeRatesService service;

	@Autowired
	public ExchangeRatesController(ExchangeRatesService service) {
		this.service = service;
	}

	@RequestMapping(value = "/rates", method = RequestMethod.GET)
	public ExchangeRatesResponse getExchangeRates(@RequestParam(value = "currency") String currency) {
		try {
			return service.getExchangeRates(currency);
		} catch (Throwable t) {
			LOG.error("Error getting exchange rates!", t);
			ExchangeRatesResponse errorResponse = new ExchangeRatesResponse();
			errorResponse.setErrorMessage(t.getMessage());
			return errorResponse;
		}
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.PUT)
	public BaseResponse refresh() {
		try {
			service.refreshData();
			BaseResponse response = new BaseResponse();
			response.setSuccess(true);
			return response;
		} catch (Throwable t) {
			LOG.error("Error refreshing server cache!", t);
			BaseResponse errorResponse = new BaseResponse();
			errorResponse.setErrorMessage(t.getMessage());
			return errorResponse;
		}
	}
	
	@RequestMapping(value = "/currencies", method = RequestMethod.GET)
	public CurrenciesListResponse getCurrenciesList() {
		try {
			return service.getCurrenciesList();
		} catch (Throwable t) {
			LOG.error("Error getting currencies list!", t);
			CurrenciesListResponse errorResponse = new CurrenciesListResponse();
			errorResponse.setErrorMessage(t.getMessage());
			return errorResponse;
		}
	}
}
