package io.github.techbank.customerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.techbank.customerservice.dtos.CustomerDTO;
import io.github.techbank.customerservice.entities.Customer;
import io.github.techbank.customerservice.exceptions.ExistsObjectInDBException;
import io.github.techbank.customerservice.exceptions.ObjectNotFoundBDException;
import io.github.techbank.customerservice.services.CustomerService;
import io.github.techbank.customerservice.utils.CustomerFactory;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private static String CUSTOMER_URL = "/customer";
    
    @MockBean
    CustomerService customerService;
    
    @Autowired
    MockMvc mockMvc;

    // SAVE CUSTOMER
    @Test
    void GivenCustomerDTO_WhenCallRegister_ThenSaveAndReturnCustomer() throws Exception {
        // Scenary
        var customer = CustomerFactory.createNewCustomer();
        BDDMockito.given(customerService.register(any(Customer.class))).willReturn(customer);

        var customerDTO = CustomerFactory.createNewCustomerDTO();
        String json = new ObjectMapper().writeValueAsString(customerDTO);

        // Execution and Verification
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty());
    }

    @Test
    void GivenCustomerDTO_WhenCallRegister_ThenThrowAnException() throws Exception {
        // Scenary
        String errorMessage = "There is already a customer with this cpf.";
        BDDMockito.given(customerService.register(any(Customer.class))).willThrow(new ExistsObjectInDBException(errorMessage));

        var customerDTO = CustomerFactory.createNewCustomerDTO();
        String json = new ObjectMapper().writeValueAsString(customerDTO);

        // Execution and Verification
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)));
    }



    // GET CUSTOMER BY CPF
    @Test
    void GivenAnCpf_WhenCallGetCustomerByCpf_ThenReturnCustomer() throws Exception {
        // Scenary
        String cpf = "123.456.789-00";
        Customer customer = CustomerFactory.createNewCustomer();
        BDDMockito.given(customerService.getByCpf(cpf)).willReturn(customer);
        CustomerDTO customerDTO = CustomerFactory.createNewCustomerDTO();

        // Execution and Verification
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(CUSTOMER_URL.concat("/" + cpf))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("cpf").value(customerDTO.cpf()))
                .andExpect(jsonPath("name").value(customerDTO.name()));

    }

    @Test
    void GivenNonExistentCustomer_WhenCallGetByCpf_ThenThrowAnException() throws Exception {
        // Scenary
        String cpf = "000.000.789-00";
        String errorMessage = "Unable to find a customer with this cpf.";
        BDDMockito.given(customerService.getByCpf(cpf)).willThrow(new ObjectNotFoundBDException(errorMessage));

        // Execution and Verification
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(CUSTOMER_URL.concat("/" + cpf))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(errorMessage));
    }

}
