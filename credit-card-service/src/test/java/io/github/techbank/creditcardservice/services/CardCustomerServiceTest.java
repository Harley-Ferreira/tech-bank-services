package io.github.techbank.creditcardservice.services;

import io.github.techbank.creditcardservice.entities.CardCustomer;
import io.github.techbank.creditcardservice.repositories.CardCustomerRepository;
import io.github.techbank.creditcardservice.services.implementations.CardCustomerServiceImpl;
import io.github.techbank.creditcardservice.utils.CardCustomerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CardCustomerServiceTest {

    CardCustomerService cardCustomerService;

    @MockBean
    CardCustomerRepository cardCustomerRepository;

    @BeforeEach
    public void setUp() {
        cardCustomerService = new CardCustomerServiceImpl(cardCustomerRepository);
    }

    // GET CREDIT CARD BY INCOME
    @Test
    void GivenANullCpf_WhenCallGetListCreditCardByCpf_ThenThrowAnException() {
        String cpf = null;

        // Execution and Verification
        var exception = assertThrows(IllegalArgumentException.class,  () -> cardCustomerService.getListCreditCardByCpf(cpf));
        var expectMessage = "Invalid object.";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void GivenAIncome_WhenCallGetListCreditCardByCpf_ThenReturnACreditCardList() {
        // Scenary
        String cpf = "123.456.789-00";
        var customer1 = CardCustomerFactory.createCustomer(1l);
        var customer2 = CardCustomerFactory.createCustomer(2l);
        List<CardCustomer> cardCustomerList = Arrays.asList(customer1, customer2);
        when(cardCustomerRepository.findAllByCpf(cpf)).thenReturn(cardCustomerList);

        // Execution and Verification
        var returnCreditCardList = cardCustomerService.getListCreditCardByCpf(cpf);

        assertEquals(cardCustomerList, returnCreditCardList);
        assertEquals(cardCustomerList.get(0), returnCreditCardList.get(0));
        assertEquals(cardCustomerList.get(1), returnCreditCardList.get(1));
    }
}
