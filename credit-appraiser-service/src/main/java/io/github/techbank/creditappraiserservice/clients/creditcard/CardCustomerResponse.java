package io.github.techbank.creditappraiserservice.clients.creditcard;

import java.math.BigDecimal;

public record CardCustomerResponse(
        CreditCardResponse creditCard
) {
    public record CreditCardResponse(
            Long id,
            String name,
            String brand,
            Double income,
            BigDecimal cardLimit) {
    }
}
