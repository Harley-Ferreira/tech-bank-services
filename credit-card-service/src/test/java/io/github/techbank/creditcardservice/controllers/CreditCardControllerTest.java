package io.github.techbank.creditcardservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.techbank.creditcardservice.entities.CreditCard;
import io.github.techbank.creditcardservice.exceptions.ExistsObjectInDBException;
import io.github.techbank.creditcardservice.exceptions.ObjectNotFoundBDException;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


    // GET CREDIT CARD BY INCOME
    @Test
    void GivenAnIncome_WhenCallGetByIncome_ThenReturnCreditCard() throws Exception {
        // Scenary
        Double income = 2.00;
        var creditCard = CreditCardFactory.createNewCredicCard();
        BDDMockito.given(creditCardService.getListCreditCardByIncome(anyDouble())).willReturn(Arrays.asList(creditCard));

        // Execution and Verification
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CREDIT_CARD_URL.concat("/" + income))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().equals(Arrays.asList(creditCard)))
                .andExpect(jsonPath("$[0].name").value("Visa Black"))
                .andExpect(jsonPath("$[0].brand").value("VISA"))
                .andExpect(jsonPath("$[0].income").value(2.00));
    }

    @Test
    void GivenNonExistentCreditCard_WhenCallGetByIncome_ThenThrowAnException() throws Exception {
        // Scenary
        Double income = 2.00;
        String errorMessage = "Unable to find a credit card with this income.";
        BDDMockito.given(creditCardService.getListCreditCardByIncome(income)).willThrow(new ObjectNotFoundBDException(errorMessage));

        // Execution and Verification
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CREDIT_CARD_URL.concat("/" + income))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(errorMessage));
    }
}
