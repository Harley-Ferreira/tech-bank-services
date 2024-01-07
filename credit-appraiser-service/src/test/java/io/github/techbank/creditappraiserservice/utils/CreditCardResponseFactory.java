package io.github.techbank.creditappraiserservice.utils;

import io.github.techbank.creditappraiserservice.clients.creditcard.CreditCardResponse;

import java.math.BigDecimal;

public class CreditCardResponseFactory {

    public static CreditCardResponse createNewCardResponse() {
        return new CreditCardResponse( 1L, "Visa Black", "VISA", new BigDecimal("8000"), new BigDecimal("20000"));
    }
}
