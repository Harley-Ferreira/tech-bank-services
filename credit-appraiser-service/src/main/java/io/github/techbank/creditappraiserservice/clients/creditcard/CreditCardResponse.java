package io.github.techbank.creditappraiserservice.clients.creditcard;

import java.math.BigDecimal;

public record CreditCardResponse(
        Long id,

        String name,

        String brand,

        BigDecimal income,

        BigDecimal cardLimit
) {
}
