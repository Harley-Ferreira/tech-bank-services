package io.github.techbank.customerservice.dtos;

import io.github.techbank.customerservice.entities.Customer;
import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(
        Long id,

        @NotBlank(message = "fill the name.")
        String name,

        @NotBlank(message = "fill the cpf.")
        String cpf,

        Integer age) {

    public CustomerDTO(Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getAge()
        );
    }
}
