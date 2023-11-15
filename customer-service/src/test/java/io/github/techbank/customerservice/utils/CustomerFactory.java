package io.github.techbank.customerservice.utils;

import io.github.techbank.customerservice.dtos.CustomerDTO;
import io.github.techbank.customerservice.entities.Customer;

public class CustomerFactory {
    public static CustomerDTO createNewCustomerDTO() {
        return new CustomerDTO(null, "123.456.789-00", "João Silva", 35);
    }

    public static Customer createNewCustomer() {
        return new Customer(1l, "123.456.789-00", "João Silva", 35);
    }
}
