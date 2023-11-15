package io.github.techbank.customerservice.services;

import io.github.techbank.customerservice.entities.Customer;

public interface CustomerService {
    Customer register(Customer any);

    Customer getByCpf(String cpf);
}
