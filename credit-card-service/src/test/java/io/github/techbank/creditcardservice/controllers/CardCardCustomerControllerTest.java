package io.github.techbank.creditcardservice.controllers;

import io.github.techbank.creditcardservice.services.CardCustomerService;
import io.github.techbank.creditcardservice.utils.CardCustomerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(CardCustomerController.class)
@AutoConfigureMockMvc
public class CardCardCustomerControllerTest {

    private final static String CARD_CUSTOMER_URL = "/credit-card/customer";
    @MockBean
    CardCustomerService cardCustomerService;

    @Autowired
    MockMvc mockMvc;

    // GET CREDIT CARD BY CPF
    @Test
    void GivenAnCpf_WhenCallGetByCpf_ThenReturnCreditCard() throws Exception {
        // Scenary
        String cpf = "123.456.789-00";
        var cardCustomer = CardCustomerFactory.createCustomer();
        BDDMockito.given(cardCustomerService.getListCreditCardByCpf(anyString())).willReturn(Arrays.asList(cardCustomer));

        // Execution and Verification
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CARD_CUSTOMER_URL.concat("/" + cpf))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().equals(Arrays.asList(cardCustomer)))
                .andExpect(jsonPath("$[0].name").value("João Silva"))
                .andExpect(jsonPath("$[0].cpf").value("123.456.789-00"))
                .andExpect(jsonPath("$[0].creditCard.id").value(1))
                .andExpect(jsonPath("$[0].creditCard.cardBrand").value("VISA"));
    }

    @Test
    void GivenNonExistentCreditCard_WhenCallGetByCpf_ThenThrowAnException() throws Exception {
        // Scenary
        String cpf = "123.456.789-00";
        BDDMockito.given(cardCustomerService.getListCreditCardByCpf(cpf)).willReturn(new ArrayList<>());

        // Execution and Verification
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CARD_CUSTOMER_URL.concat("/" + cpf))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }
}
