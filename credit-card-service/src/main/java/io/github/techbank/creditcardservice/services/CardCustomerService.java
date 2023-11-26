package io.github.techbank.creditcardservice.services;

import io.github.techbank.creditcardservice.entities.CardCustomer;

import java.util.List;

public interface CardCustomerService {

    CardCustomer register(CardCustomer cardCustomer);

    List<CardCustomer> getListCreditCardByCpf(String cpf);
}
