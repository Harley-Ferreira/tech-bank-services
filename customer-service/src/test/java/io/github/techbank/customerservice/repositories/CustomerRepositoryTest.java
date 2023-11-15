package io.github.techbank.customerservice.repositories;

import io.github.techbank.customerservice.entities.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static io.github.techbank.customerservice.utils.CustomerFactory.createNewCustomer;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class CustomerRepositoryTest {
    
    @Autowired
    TestEntityManager entityManager;
    
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void GivenCustomer_WhenCallExistsByCpf_ThenReturnTrue() {
        // Scenary
        var customer = createNewCustomer();
        entityManager.merge(customer);

        // Execution
        var cpf = "123.456.789-00";
        boolean exist = customerRepository.existsByCpf(cpf);

        // Verification
        Assertions.assertTrue(exist);
    }

    @Test
    void GivenNonExistentCustomer_WhenCallExistsByCpf_ThenReturnFalse() {
        // Scenary

        // Execution
        var cpf = "122.456.789-00";
        boolean exist = customerRepository.existsByCpf(cpf);

        // Verification
        Assertions.assertFalse(exist);
    }

    @Test
    void GivenCpf_WhenCallFindByCpf_ThenReturnCustomerOptional() {
        // Scenary
        var customer = createNewCustomer();
        entityManager.merge(customer);

        // Execution
        var cpf = "123.456.789-00";
        Optional<Customer> optional = customerRepository.findByCpf(cpf);

        // Verification
        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    void GivenCpf_WhenCallFindByCpf_ThenReturnEmptyOptional() {
        // Scenary

        // Execution
        var cpf = "123.456.789-00";
        Optional<Customer> optional = customerRepository.findByCpf(cpf);

        // Verification
        Assertions.assertFalse(optional.isPresent());
    }
}
