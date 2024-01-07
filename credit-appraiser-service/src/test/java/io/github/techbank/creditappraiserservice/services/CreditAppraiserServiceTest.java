package io.github.techbank.creditappraiserservice.services;

import io.github.techbank.creditappraiserservice.clients.creditcard.CreditCardClient;
import io.github.techbank.creditappraiserservice.clients.creditcard.CreditCardResponse;
import io.github.techbank.creditappraiserservice.clients.customer.CustomerClient;
import io.github.techbank.creditappraiserservice.clients.customer.CustomerResponse;
import io.github.techbank.creditappraiserservice.dtos.CardApprovedDTO;
import io.github.techbank.creditappraiserservice.services.implementations.CreditAppraiserServiceImpl;
import io.github.techbank.creditappraiserservice.utils.CreditCardResponseFactory;
import io.github.techbank.creditappraiserservice.utils.CustomerResponseFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CreditAppraiserServiceTest {

    CreditAppraiserService creditAppraiserService;

    @MockBean
    CreditCardClient creditCardClient;
    @MockBean
    CustomerClient customerClient;

    @BeforeEach
    public void setUp() {
        creditAppraiserService = new CreditAppraiserServiceImpl(creditCardClient, customerClient);
    }

    // EVALUATE
    @Test
    void GivenNullCpf_WhenCallEvaluate_ThenThrowAnIllegalArgumentException() {
        String cpf = null;
        BigDecimal income = new BigDecimal("10000");

        // Execution and Verification
        var exception = assertThrows(IllegalArgumentException.class,  () -> creditAppraiserService.evaluate(cpf, income));
        var expectMessage = "Invalid object.";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void GivenNullIncome_WhenCallEvaluate_ThenThrowAnIllegalArgumentException() {
        String cpf = "000.000.000-72";
        BigDecimal income = null;

        // Execution and Verification
        var exception = assertThrows(IllegalArgumentException.class,  () -> creditAppraiserService.evaluate(cpf, income));
        var expectMessage = "Invalid object.";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void GivenCpfAndIncome_WhenCallEvaluate_ThenMakeEvaluationAndReturnAListCardAppraised() {
        String cpf = "123.456.789-72";
        BigDecimal income = new BigDecimal(10000);
        CustomerResponse customerResponse = CustomerResponseFactory.createNewCustomerResponse();
        List<CreditCardResponse> creditCardResponse = List.of(CreditCardResponseFactory.createNewCardResponse());
        when(customerClient.getCustomerByCpf(cpf)).thenReturn(ResponseEntity.ok(customerResponse));
        when(creditCardClient.getAllCardByIncome(income)).thenReturn(ResponseEntity.ok(creditCardResponse));

        // Execution
        List<CardApprovedDTO> returned = creditAppraiserService.evaluate(cpf, income);

        // Verification
        Assertions.assertEquals("VISA", returned.get(0).cardBrand());
        Assertions.assertEquals(new BigDecimal("44000.0"), returned.get(0).approvedCardLimit());
    }
}
