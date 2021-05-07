package eu.automationlabs.exchangerates.dao.currencylayer;

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

import eu.automationlabs.exchangerates.dao.currencylayer.ExchangeRatesDaoCurrencyLayerImpl;
import eu.automationlabs.exchangerates.domain.BaseResponse;
import eu.automationlabs.exchangerates.domain.CurrenciesListResponse;
import eu.automationlabs.exchangerates.domain.ExchangeRatesResponse;
import eu.automationlabs.exchangerates.domain.currencylayer.LiveResponse;
import eu.automationlabs.exchangerates.exception.CurrencyServerException;
import eu.automationlabs.exchangerates.service.ExchangeRatesService;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRatesDaoCurrencyLayerImplTest {

	private static final String CURRECY_LAYER_URL = "http://apilayer.net/api/";

	private static final String ACCESS_KEY = "ac";

	private ExchangeRatesDaoCurrencyLayerImpl instance;

	@Mock
	private RestTemplate restTemplateMock;

	@Mock
	private LiveResponse liveResponseMock;

	@Mock
	eu.automationlabs.exchangerates.domain.currencylayer.BaseResponse.Error errorMock;

	@Before
	public void setUp() {
		instance = new ExchangeRatesDaoCurrencyLayerImpl(CURRECY_LAYER_URL, ACCESS_KEY);
		instance.restTemplate = restTemplateMock;
	}

	@Test
	public void get_exchange_rates_calls_correct_url() {
		String expectedUrl = CURRECY_LAYER_URL + "live?access_key=" + ACCESS_KEY;
		when(restTemplateMock.getForObject(expectedUrl, LiveResponse.class)).thenReturn(liveResponseMock);
		when(liveResponseMock.isSuccess()).thenReturn(true);
		instance.getExchangeRates();
		verify(restTemplateMock).getForObject(expectedUrl, LiveResponse.class);
	}

	@Test
	public void get_exchange_rates_throws_exception() {
		String expectedUrl = CURRECY_LAYER_URL + "live?access_key=" + ACCESS_KEY;
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
