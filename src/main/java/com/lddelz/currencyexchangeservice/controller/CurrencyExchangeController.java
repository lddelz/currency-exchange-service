package com.lddelz.currencyexchangeservice.controller;

import com.lddelz.currencyexchangeservice.model.CurrencyExchange;
import com.lddelz.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@RestController
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final Environment environment;

    public CurrencyExchangeController(CurrencyExchangeRepository currencyExchangeRepository, Environment environment) {
        this.currencyExchangeRepository = currencyExchangeRepository;
        this.environment = environment;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) throws UnknownHostException {
        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);

        if (currencyExchange == null) {
            throw new RuntimeException("Unable to Find data for " + from + " to " + to);
        }
        logger.info("Currency exchange service host: {}", Inet4Address.getLocalHost().getHostAddress());
        currencyExchange.setPort(System.getenv("HOSTNAME"));
        return currencyExchange;
    }

}
