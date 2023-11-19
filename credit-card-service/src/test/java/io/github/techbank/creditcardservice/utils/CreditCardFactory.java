package io.github.techbank.creditcardservice.utils;

import io.github.techbank.creditcardservice.dtos.CreditCardDTO;
import io.github.techbank.creditcardservice.entities.CreditCard;
import io.github.techbank.creditcardservice.enums.CardBrand;

import java.math.BigDecimal;

public class CreditCardFactory {
    private final static BigDecimal cardLimit = new BigDecimal(100000.00);
    public static CreditCard createNewCredicCard() {
        return new CreditCard(1L, "Visa Black", CardBrand.VISA, 2.00, cardLimit);
    }

    public static CreditCard createNewCredicCard(Long id) {
        return new CreditCard(id, "Visa Black", CardBrand.VISA, 2.00, cardLimit);
    }

    public static CreditCardDTO createNewCredicCardDTO(Long id) {
        return new CreditCardDTO(id, "Visa Black", CardBrand.VISA.name(), 2.00, cardLimit);
    }
}
