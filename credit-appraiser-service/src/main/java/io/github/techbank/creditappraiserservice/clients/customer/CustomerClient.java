package io.github.techbank.creditappraiserservice.clients.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "customer-service", path = "customer")
public interface CustomerClient {

    @GetMapping("/{cpf}")
    ResponseEntity<CustomerResponse> getCustomerByCpf(@PathVariable("cpf") String cpf);
}
