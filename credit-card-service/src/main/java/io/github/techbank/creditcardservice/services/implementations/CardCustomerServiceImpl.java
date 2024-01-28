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
    public CardCustomer save(CardCustomer creditCard) {
        if (creditCard == null || creditCard.getId() != null) {
            throw new IllegalArgumentException("Invalid object.");
        }

        if (creditCard.getCpf() == null || creditCard.getCpf().isBlank()) {
            throw new IllegalArgumentException("Invalid cpf.");
        }
        return cardCustomerRepository.save(creditCard);
    }

    @Override
    public List<CardCustomer> getListCreditCardByCpf(String cpf) {
        if (cpf == null) throw new IllegalArgumentException("Invalid cpf.");
        return cardCustomerRepository.findAllByCpf(cpf);
    }
}
