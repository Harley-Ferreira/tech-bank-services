package io.github.techbank.creditcardservice.repositories;

import io.github.techbank.creditcardservice.entities.CreditCard;
import io.github.techbank.creditcardservice.enums.CardBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    boolean existsByCardBrandAndCardLimitAndIncome(CardBrand cardBrand, BigDecimal cardLimit, Double income);

    List<CreditCard> findAllByIncomeLessThanEqual(Double income);
}
