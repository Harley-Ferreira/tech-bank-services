package io.github.techbank.creditcardservice.services;

import io.github.techbank.creditcardservice.entities.CreditCard;

import java.util.List;

public interface CreditCardService {
    CreditCard register(CreditCard creditCard);

    List<CreditCard> getListCreditCardByIncome(Double income);
}
