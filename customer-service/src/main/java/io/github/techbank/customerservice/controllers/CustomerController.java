package io.github.techbank.customerservice.controllers;

import io.github.techbank.customerservice.dtos.CustomerDTO;
import io.github.techbank.customerservice.entities.Customer;
import io.github.techbank.customerservice.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO, UriComponentsBuilder uriComponents) {
        var customer = new Customer(customerDTO);
        customer = customerService.register(customer);
        customerDTO = new CustomerDTO(customer);
        var uri = uriComponents.path("/customer/{cpf}").buildAndExpand(customer.getCpf()).toUri();
        return ResponseEntity.created(uri).body(customerDTO);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("cpf") String cpf) {
        var customer = customerService.getByCpf(cpf);
        return ResponseEntity.ok(new CustomerDTO(customer));
    }
}
