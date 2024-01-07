package io.github.techbank.creditappraiserservice.utils;

import io.github.techbank.creditappraiserservice.dtos.CardApprovedDTO;

import java.math.BigDecimal;

public class ApprovedCreditFactory {

    private static String BRAND = "VISA";
    private final static BigDecimal approvedLimit = new BigDecimal("100000.00");

    public static CardApprovedDTO createNewCardApprovedDTO() {
        return new CardApprovedDTO( "Visa Black", BRAND, approvedLimit);
    }
}
