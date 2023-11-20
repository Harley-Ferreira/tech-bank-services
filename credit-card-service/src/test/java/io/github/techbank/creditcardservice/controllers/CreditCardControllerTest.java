package io.github.techbank.creditcardservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.techbank.creditcardservice.entities.CreditCard;
import io.github.techbank.creditcardservice.exceptions.ExistsObjectInDBException;
import io.github.techbank.creditcardservice.services.CreditCardService;
import io.github.techbank.creditcardservice.utils.CreditCardFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(CreditCardController.class)
@AutoConfigureMockMvc
public class CreditCardControllerTest {

    private final static String CREDIT_CARD_URL = "/credit-card";
    
    @MockBean
    CreditCardService creditCardService;
    
    @Autowired
    MockMvc mockMvc;
    
    // SAVE CUSTOMER
    @Test
    void GivenCreditCardDTO_WhenCallRegister_ThenSaveAndReturnCreditCard() throws Exception {
        // Scenary
        var creditCard = CreditCardFactory.createNewCredicCard();
        BDDMockito.given(creditCardService.register(any(CreditCard.class))).willReturn(creditCard);

        var creditCardDTO = CreditCardFactory.createNewCredicCardDTO(null);
        String json = new ObjectMapper().writeValueAsString(creditCardDTO);

        // Execution and Verification
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CREDIT_CARD_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty());
    }

    @Test
    void GivenCreditCardDTO_WhenCallRegister_ThenThrowAnException() throws Exception {
        // Scenary
        String errorMessage = "There is already a creditCard with this brand.";
        BDDMockito.given(creditCardService.register(any(CreditCard.class))).willThrow(new ExistsObjectInDBException(errorMessage));

        var creditCardDTO = CreditCardFactory.createNewCredicCardDTO(null);
        String json = new ObjectMapper().writeValueAsString(creditCardDTO);

        // Execution and Verification
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CREDIT_CARD_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)));
    }
}
