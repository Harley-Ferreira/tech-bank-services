package io.github.techbank.creditcardservice.services.implementations;

import io.github.techbank.creditcardservice.entities.CreditCard;
import io.github.techbank.creditcardservice.exceptions.ExistsObjectInDBException;
import io.github.techbank.creditcardservice.repositories.CreditCardRepository;
import io.github.techbank.creditcardservice.services.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    @Override
    public CreditCard register(CreditCard creditCard) {
        if (creditCard == null || Objects.equals(creditCard, new CreditCard())) throw new IllegalArgumentException("Invalid object.");

        boolean exists = creditCardRepository.existsByCardBrandAndCardLimitAndIncome(creditCard.getCardBrand(), creditCard.getCardLimit(), creditCard.getIncome());
        if (exists) throw new ExistsObjectInDBException("There is already a credit card with this properties.");
        return creditCardRepository.save(creditCard);
    }

    @Override
    public List<CreditCard> getListCreditCardByIncome(Double income) {
        if (income == null) throw new IllegalArgumentException("Invalid object.");
        return creditCardRepository.findAllByIncomeLessThanEqual(income);
    }
}
