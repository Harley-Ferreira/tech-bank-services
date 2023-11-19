package io.github.techbank.creditcardservice.repositories;

import io.github.techbank.creditcardservice.entities.CreditCard;
import io.github.techbank.creditcardservice.enums.CardBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    boolean existsByCardBrandAndCardLimitAndIncame(CardBrand cardBrand, BigDecimal cardLimit, Double incame);
}
