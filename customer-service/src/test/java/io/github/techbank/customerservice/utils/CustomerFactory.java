package io.github.techbank.customerservice.utils;

import io.github.techbank.customerservice.dtos.CustomerDTO;
import io.github.techbank.customerservice.entities.Customer;

public class CustomerFactory {
    public static CustomerDTO createNewCustomerDTO() {
        return new CustomerDTO(null, "João Silva", "123.456.789-00", 35);
    }

    public static Customer createNewCustomer(Long id) {
        return new Customer(id, "João Silva", "123.456.789-00", 35);
    }

    public static Customer createNewCustomer() {
        return new Customer(1l, "João Silva", "123.456.789-00",  35);
    }
}
