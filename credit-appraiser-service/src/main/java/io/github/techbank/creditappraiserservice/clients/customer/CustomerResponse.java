package io.github.techbank.creditappraiserservice.clients.customer;

public record CustomerResponse(Long id, String name, String cpf, Integer age) {
}
