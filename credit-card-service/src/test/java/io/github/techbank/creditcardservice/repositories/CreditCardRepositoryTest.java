package io.github.techbank.creditcardservice.repositories;

import io.github.techbank.creditcardservice.entities.CreditCard;
import io.github.techbank.creditcardservice.utils.CreditCardFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class CreditCardRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Test
    void GivenAnIncome_WhenCallFindAllByIncomeLessThanEqual_ThenReturnEmptyList() {
        double income = 2.0;
        List<CreditCard> returnedList = creditCardRepository.findAllByIncomeLessThanEqual(income);

        Assertions.assertNotNull(returnedList);
        Assertions.assertEquals(0, returnedList.size());
    }

    @Test
    void GivenAnIncome_WhenCallFindAllByIncomeLessThanEqual_ThenReturnCreditCardList() {
        // Scenary
        double income = 2.0;
        var creditCard1 = CreditCardFactory.createNewCredicCard(null);
        var creditCard2 = CreditCardFactory.createNewCredicCard(null);
        var creditCard3 = CreditCardFactory.createNewCredicCard(null);
        creditCard3.setIncome(23d);

        entityManager.merge(creditCard1);
        entityManager.merge(creditCard2);

        // Execution
        List<CreditCard> returnedList = creditCardRepository.findAllByIncomeLessThanEqual(income);

        // Verification
        Assertions.assertNotNull(returnedList);
        Assertions.assertEquals(2, returnedList.size());
    }
}
