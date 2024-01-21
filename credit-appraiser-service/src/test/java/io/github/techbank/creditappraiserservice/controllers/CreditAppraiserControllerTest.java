package io.github.techbank.creditappraiserservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.techbank.creditappraiserservice.exceptions.ObjectNotFoundFeignException;
import io.github.techbank.creditappraiserservice.infra.mqueue.DataSolicitationCardQDTO;
import io.github.techbank.creditappraiserservice.services.CreditAppraiserService;
import io.github.techbank.creditappraiserservice.utils.ApprovedCreditFactory;
import io.github.techbank.creditappraiserservice.utils.DataSolicitationCardQDTOFactory;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(CreditAppraiserController.class)
@AutoConfigureMockMvc
public class CreditAppraiserControllerTest {

    private final static String CREDIT_CARD_URL = "/credit-appraiser";
    
    @MockBean
    CreditAppraiserService creditAppraiserService;
    
    @Autowired
    MockMvc mockMvc;
    
    // EVALUATE
    @Test
    void GivenCpfAndIncome_WhenCallEvaluate_ThenReturnCreditCardApprovedList() throws Exception {
        // Scenary
        var creditCardApproved = ApprovedCreditFactory.createNewCardApprovedDTO();
        BDDMockito.given(creditAppraiserService.evaluate(anyString(), any(BigDecimal.class))).willReturn(List.of(creditCardApproved));

        // Execution and Verification
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CREDIT_CARD_URL.concat("/make-evaluation/000.000.00-00/10000"))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().equals(List.of(creditCardApproved)));
    }



    @Test
    void GivenCpfAndIncome_WhenCallEvaluate_ThenThrowAnObjectNotFoundFeignException() throws Exception {
        // Scenary
        String errorMessage = "It was not possible the find a customer with this cpf.";
        BDDMockito.given(creditAppraiserService.evaluate(anyString(), any(BigDecimal.class))).willThrow(new ObjectNotFoundFeignException(errorMessage));

        // Execution and Verification
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CREDIT_CARD_URL.concat("/make-evaluation/000.000.00-00/10000"))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(errorMessage));
    }

    // REQUEST CARD
    @Test
    void GivenDataSolicitationCardQDTO_WhenCallRequestCard_ThenReturnAProtocol() throws Exception {
        // Scenary
        var dataSolicitationCardQDTO = DataSolicitationCardQDTOFactory.createNewDataSolicitationCardQDTO();
        var protocol = UUID.randomUUID().toString();
        BDDMockito.given(creditAppraiserService.requestCard(any(DataSolicitationCardQDTO.class))).willReturn(protocol);

        // Execution and Verification
        String json = new ObjectMapper().writeValueAsString(dataSolicitationCardQDTO);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CREDIT_CARD_URL.concat("/request-card"))
                .content(json)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().equals(protocol));
    }
}
