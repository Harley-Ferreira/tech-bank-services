package io.github.techbank.creditcardservice.repositories;

import io.github.techbank.creditcardservice.entities.CardCustomer;
import io.github.techbank.creditcardservice.entities.CreditCard;
import io.github.techbank.creditcardservice.utils.CardCustomerFactory;
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

import static io.github.techbank.creditcardservice.utils.CreditCardFactory.createNewCredicCard;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class CardCustomerRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CardCustomerRepository cardCustomerRepository;

    @Test
    void GivenAnCpf_WhenCallFindAllByCpf_ThenReturnEmptyList() {
        String cpf = "123.456.789-00";
        List<CardCustomer> returnedList = cardCustomerRepository.findAllByCpf(cpf);

        Assertions.assertNotNull(returnedList);
        Assertions.assertEquals(0, returnedList.size());
    }

    @Test
    void GivenAnCpf_WhenCallFindAllByCpf_ThenReturnCreditCardList() {
        // Scenary
        String cpf = "123.456.789-00";
        CreditCard creditCard = entityManager.merge(createNewCredicCard(1l));
        var customer1 = CardCustomerFactory.createCustomer(1l);
        customer1.setCreditCard(creditCard);
        var customer2 = CardCustomerFactory.createCustomer(2l);
        customer2.setCreditCard(creditCard);
        entityManager.merge(customer1);
        entityManager.merge(customer2);

        // Execution
        List<CardCustomer> returnedList = cardCustomerRepository.findAllByCpf(cpf);

        // Verification
        Assertions.assertNotNull(returnedList);
        Assertions.assertEquals(2, returnedList.size());
    }
}
