package io.github.techbank.creditappraiserservice.utils;

import io.github.techbank.creditappraiserservice.clients.customer.CustomerResponse;

public class CustomerResponseFactory {

    public static CustomerResponse createNewCustomerResponse() {
        return new CustomerResponse( 1L, "Max", "123.456.789-72", 22);
    }
}
