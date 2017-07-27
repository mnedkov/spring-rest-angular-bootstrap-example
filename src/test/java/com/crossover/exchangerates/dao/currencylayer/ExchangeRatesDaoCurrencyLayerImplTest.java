package com.crossover.exchangerates.dao.currencylayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.crossover.exchangerates.dao.currencylayer.ExchangeRatesDaoCurrencyLayerImpl;
import com.crossover.exchangerates.domain.BaseResponse;
import com.crossover.exchangerates.domain.CurrenciesListResponse;
import com.crossover.exchangerates.domain.ExchangeRatesResponse;
import com.crossover.exchangerates.domain.currencylayer.LiveResponse;
import com.crossover.exchangerates.exception.CurrencyServerException;
import com.crossover.exchangerates.service.ExchangeRatesService;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRatesDaoCurrencyLayerImplTest {

	private static final String ACCESS_KEY = "ac";

	private ExchangeRatesDaoCurrencyLayerImpl instance;

	@Mock
	private RestTemplate restTemplateMock;

	@Mock
	private LiveResponse liveResponseMock;

	@Mock
	com.crossover.exchangerates.domain.currencylayer.BaseResponse.Error errorMock;

	@Before
	public void setUp() {
		instance = new ExchangeRatesDaoCurrencyLayerImpl(ACCESS_KEY);
		instance.restTemplate = restTemplateMock;
	}

	@Test
	public void get_exchange_rates_calls_correct_url() {
		String expectedUrl = "http://apilayer.net/api/live?access_key=" + ACCESS_KEY;
		when(restTemplateMock.getForObject(expectedUrl, LiveResponse.class)).thenReturn(liveResponseMock);
		when(liveResponseMock.isSuccess()).thenReturn(true);
		instance.getExchangeRates();
		verify(restTemplateMock).getForObject(expectedUrl, LiveResponse.class);
	}

	@Test
	public void get_exchange_rates_throws_exception() {
		String expectedUrl = "http://apilayer.net/api/live?access_key=" + ACCESS_KEY;
		when(restTemplateMock.getForObject(expectedUrl, LiveResponse.class)).thenReturn(liveResponseMock);
		when(liveResponseMock.isSuccess()).thenReturn(false);
		when(liveResponseMock.getError()).thenReturn(errorMock);
		String errorMessage = "Currency Server Down!";
		when(errorMock.getInfo()).thenReturn(errorMessage);
		try {
			instance.getExchangeRates();
			fail("Expected exception not thrown");
		} catch (CurrencyServerException e) {
			assertEquals("Wrong error message", errorMessage, e.getMessage());
		}
	}

}
