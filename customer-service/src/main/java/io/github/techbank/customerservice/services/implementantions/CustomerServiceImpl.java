package io.github.techbank.customerservice.services.implementantions;

import io.github.techbank.customerservice.entities.Customer;
import io.github.techbank.customerservice.exceptions.ExistsObjectInDBException;
import io.github.techbank.customerservice.exceptions.ObjectNotFoundBDException;
import io.github.techbank.customerservice.repositories.CustomerRepository;
import io.github.techbank.customerservice.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer register(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Invalid object.");
        boolean exists = customerRepository.existsByCpf(customer.getCpf());
        if (exists) throw new ExistsObjectInDBException("There is already a customer with this cpf.");
        return customerRepository.save(customer);
    }

    @Override
    public Customer getByCpf(String cpf) {
        if (cpf == null) throw new IllegalArgumentException("Invalid object.");
        return customerRepository.findByCpf(cpf).orElseThrow(() -> new ObjectNotFoundBDException("Unable to find a customer with this cpf."));
    }
}
