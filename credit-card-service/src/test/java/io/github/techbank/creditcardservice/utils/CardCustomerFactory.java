package io.github.techbank.creditcardservice.utils;


import io.github.techbank.creditcardservice.entities.CardCustomer;

import static io.github.techbank.creditcardservice.utils.CreditCardFactory.createNewCredicCard;

public class CardCustomerFactory {

    public static CardCustomer createCustomer() {
        return new CardCustomer(1l, "João Silva", "123.456.789-00", 35, createNewCredicCard(), 100000.00);
    }

    public static CardCustomer createCustomer(Long id) {
        return new CardCustomer(id, "João Silva", "123.456.789-00", 35, createNewCredicCard(), 100000.00);
    }

}
