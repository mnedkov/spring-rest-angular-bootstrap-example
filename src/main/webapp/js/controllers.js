var exchangeRatesApp = angular.module('ExchangeRates', [ 'ngAnimate',
		'ui.bootstrap', 'ngSanitize', 'ui.select', 'ngResource' ]);

exchangeRatesApp.controller('ExchangeRatesCtrl', function($scope, $resource) {
	$scope.baseCurrency = "USD";
	$scope.currencies = [];
	$scope.exchangeRates = [];
	$scope.currencySearch = "";
	$scope.lastRatesRefresh = "";
	$scope.amountBaseCurrency = 1;

	var ratesResource = $resource('rest/rates');
	fetchExchangeRates = function(currencyCode) {
		var exchangeRatesResponse = ratesResource.get({
			currency : currencyCode
		}, function() {
			if (exchangeRatesResponse.success) {
				$scope.exchangeRates = exchangeRatesResponse.exchangeRates;
			} else {
				$scope.addMessage("danger", exchangeRatesResponse.errorMessage);
			}
		});
	};

	var currenciesResource = $resource('rest/currencies');
	fetchCurrencies = function() {
		var currenciesListResponse = currenciesResource.get(function() {
			if (currenciesListResponse.success) {
				$scope.currencies = currenciesListResponse.currencyCodes;
			} else {
				$scope.addMessage("danger", currenciesListResponse.errorMessage);
			}
		});
	};

	var refreshResource = $resource('rest/refresh', null, {
		'update' : {
			method : 'PUT'
		}
	});
	$scope.refresh = function() {
		var refreshResponse = refreshResource.update(function() {
			if (refreshResponse.success) {
				$scope.init();
				$scope.addMessage("success", 'Successfully refreshed server data!');
			} else {
				$scope.addMessage("danger", refreshResponse.errorMessage);
			}
		});
	};

	$scope.onCurrencySelect = function($item) {
		fetchExchangeRates($item);
	};

	$scope.alerts = [];

	$scope.addMessage = function(type, msg) {
		$scope.alerts.push({
			type : type,
			msg : msg
		});
	};

	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

	$scope.init = function() {
		fetchCurrencies();
		fetchExchangeRates($scope.baseCurrency);
		$scope.lastRatesRefresh = new Date();
	};

	$scope.init();
});