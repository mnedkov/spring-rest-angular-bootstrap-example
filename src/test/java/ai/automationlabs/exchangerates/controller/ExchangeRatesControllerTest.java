package ai.automationlabs.exchangerates.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ai.automationlabs.exchangerates.domain.BaseResponse;
import ai.automationlabs.exchangerates.domain.CurrenciesListResponse;
import ai.automationlabs.exchangerates.domain.ExchangeRatesResponse;
import ai.automationlabs.exchangerates.exception.CurrencyServerException;
import ai.automationlabs.exchangerates.service.ExchangeRatesService;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRatesControllerTest {

	@Mock
	private ExchangeRatesService serviceMock;

	private ExchangeRatesController instance;

	@Before
	public void setUp() {
		instance = new ExchangeRatesController(serviceMock);
	}
	
	@Test
	public void get_exchange_rates_returns_success_response() {
		ExchangeRatesResponse expectedResponse = new ExchangeRatesResponse();
		when(serviceMock.getExchangeRates(anyString())).thenReturn(expectedResponse);
		ExchangeRatesResponse response = instance.getExchangeRates("USD");
		assertEquals("Response does not match!", expectedResponse, response );
	}

	@Test
	public void get_exchange_rates_converts_all_exceptions_to_error_response() {
		when(serviceMock.getExchangeRates(anyString())).thenThrow(new RuntimeException());
		ExchangeRatesResponse response = instance.getExchangeRates("USD");
		assertFalse("Expected that success flag is false when exception is thrown!", response.isSuccess());
	}

	@Test
	public void get_exchange_rates_returns_response_with_correct_error_message() {
		String errorMessage = "Currency server not available";
		when(serviceMock.getExchangeRates(anyString())).thenThrow(new CurrencyServerException(errorMessage));
		ExchangeRatesResponse response = instance.getExchangeRates("USD");
		assertEquals("Incorrect error message!", errorMessage, response.getErrorMessage());
	}
	
	
	
	@Test
	public void get_currencies_returns_success_response() {
		CurrenciesListResponse expectedResponse = new CurrenciesListResponse();
		when(serviceMock.getCurrenciesList()).thenReturn(expectedResponse);
		CurrenciesListResponse response = instance.getCurrenciesList();
		assertEquals("Response does not match!", expectedResponse, response );
	}

	@Test
	public void get_currecnies_list_converts_all_exceptions_to_error_response() {
		when(serviceMock.getCurrenciesList()).thenThrow(new RuntimeException());
		CurrenciesListResponse response = instance.getCurrenciesList();
		assertFalse("Expected that success flag is false when exception is thrown!", response.isSuccess());
	}

	@Test
	public void get_currencies_list_returns_response_with_correct_error_message() {
		String errorMessage = "Currency server not available";
		when(serviceMock.getCurrenciesList()).thenThrow(new CurrencyServerException(errorMessage));
		CurrenciesListResponse response = instance.getCurrenciesList();
		assertEquals("Incorrect error message!", errorMessage, response.getErrorMessage());
	}
	
	@Test
	public void refresh_converts_all_exceptions_to_error_response() {
		doThrow(new RuntimeException()).when(serviceMock).refreshData();
		BaseResponse response = instance.refresh();
		assertFalse("Expected that success flag is false when exception is thrown!", response.isSuccess());
	}

	@Test
	public void refresh_returns_response_with_correct_error_message() {
		String errorMessage = "Currency server not available";
		doThrow(new CurrencyServerException(errorMessage)).when(serviceMock).refreshData();
		BaseResponse response = instance.refresh();
		assertEquals("Incorrect error message!", errorMessage, response.getErrorMessage());
	}
	
	
}
