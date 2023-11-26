package io.github.techbank.creditcardservice.dtos;

import io.github.techbank.creditcardservice.entities.CardCustomer;
import io.github.techbank.creditcardservice.entities.CreditCard;

public record CardCustomerDTO(
        Long id,

        String name,

        String cpf,

        Integer age,

        Double cardLimit,

        CreditCard creditCard

) {
    public CardCustomerDTO(CardCustomer cardCustomer) {
        this(
                cardCustomer.getId(),
                cardCustomer.getName(),
                cardCustomer.getCpf(),
                cardCustomer.getAge(),
                cardCustomer.getCardLimit(),
                cardCustomer.getCreditCard()
        );
    }
}
