package io.github.techbank.customerservice.services;

import io.github.techbank.customerservice.entities.Customer;
import io.github.techbank.customerservice.exceptions.ExistsObjectInDBException;
import io.github.techbank.customerservice.exceptions.ObjectNotFoundBDException;
import io.github.techbank.customerservice.repositories.CustomerRepository;
import io.github.techbank.customerservice.services.implementantions.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static io.github.techbank.customerservice.utils.CustomerFactory.createNewCustomer;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CustomerServiceTest {

    CustomerService customerService;
    
    @MockBean
    CustomerRepository customerRepository;
    
    @BeforeEach
    public void setUp() {
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void GivenACustomer_WhenCallRegister_ThenSaveAndReturnCustomer() {
        // Scenary
        var request = createNewCustomer(null);
        var customer = createNewCustomer();
        when(customerRepository.existsByCpf(anyString())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Execution
        var registedCustomer = customerService.register(request);

        // Verification
        verify(customerRepository, times(1)).save(request);
        assertNotNull(registedCustomer.getId());
        assertEquals(request.getCpf(), registedCustomer.getCpf());
        assertEquals(request.getName(), registedCustomer.getName());
        assertEquals(request.getAge(), registedCustomer.getAge());
    }

    @Test
    void GivenACustomer_WhenCallRegister_ThenThrowAnException() {
        // Scenary
        var request = createNewCustomer(null);
        when(customerRepository.existsByCpf(anyString())).thenReturn(true);

        // Execution and Verification
        ExistsObjectInDBException exception = assertThrows(ExistsObjectInDBException.class, () -> customerService.register(request));
        assertEquals("There is already a customer with this cpf.", exception.getMessage());
        verify(customerRepository, never()).save(request);
    }

    @Test
    void GivenNullCustomer_WhenCallRegister_ThenThrowAnException() {
        // Scenary
        Customer request = null;

        // Execution
        var exception = assertThrows(IllegalArgumentException.class, () -> customerService.register(request));

        // Verification
        var expectedMessage = "Invalid object.";
        assertEquals(exception.getMessage(), expectedMessage);
    }

    // GET BY ID
    @Test
    void GivenExistingCpf_WhenCallGetByCpf_ThenReturnCustomer() {
        // Scenary
        String request = "123.456.789-00";
        var customer = createNewCustomer(2L);
        when(customerRepository.findByCpf(anyString())).thenReturn(Optional.of(customer));

        // Execution
        var foundCustomer = customerService.getByCpf(request);

        // Verification
        assertNotNull(foundCustomer.getId());
        System.out.println(foundCustomer);
        assertEquals(request, foundCustomer.getCpf());
    }

    @Test
    void GivenNonExistingCpf_WhenCallGetByCpf_ThenThrowAnException() {
        // Scenary
        String request = "122.456.789-00";
        when(customerRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        // Execution
        var exception = assertThrows(ObjectNotFoundBDException.class, () -> customerService.getByCpf(request));

        // Verification
        assertEquals("Unable to find a customer with this cpf.", exception.getMessage());
    }

    @Test
    void GivenNullCpf_WhenCallGetByCpf_ThenThrowAnException() {
        // Scenary
        String request = null;

        // Execution and Verification
        var exception = assertThrows(IllegalArgumentException.class, () -> customerService.getByCpf(request));
        assertEquals("Invalid object.", exception.getMessage());
    }
}
