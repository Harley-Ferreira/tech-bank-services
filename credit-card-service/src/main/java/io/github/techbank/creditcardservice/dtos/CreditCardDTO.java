package io.github.techbank.creditcardservice.dtos;

import io.github.techbank.creditcardservice.entities.CreditCard;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreditCardDTO(
        Long id,

        @NotBlank(message = "fill the name.")
        String name,

        @NotBlank(message = "fill the brand.")
        String brand,

        @NotNull(message = "fill the incame.")
        Double incame,

        @NotNull(message = "fill the card limit.")
        BigDecimal cardLimit) {

    public CreditCardDTO(CreditCard creditCard) {
        this(
                creditCard.getId(),
                creditCard.getName(),
                creditCard.getCardBrand().name(),
                creditCard.getIncame(),
                creditCard.getCardLimit()
        );
    }

}
