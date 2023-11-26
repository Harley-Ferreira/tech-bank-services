package io.github.techbank.creditcardservice.services.implementations;

import io.github.techbank.creditcardservice.entities.CardCustomer;
import io.github.techbank.creditcardservice.repositories.CardCustomerRepository;
import io.github.techbank.creditcardservice.services.CardCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardCustomerServiceImpl implements CardCustomerService {

    private final CardCustomerRepository cardCustomerRepository;

    @Override
    public CardCustomer register(CardCustomer creditCard) {
        return null;
    }

    @Override
    public List<CardCustomer> getListCreditCardByCpf(String cpf) {
        if (cpf == null) throw new IllegalArgumentException("Invalid object.");
        return cardCustomerRepository.findAllByCpf(cpf);
    }
}
