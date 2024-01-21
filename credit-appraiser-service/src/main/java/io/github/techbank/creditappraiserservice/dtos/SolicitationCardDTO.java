package io.github.techbank.creditappraiserservice.dtos;

import java.math.BigDecimal;

public record SolicitationCardDTO(
        Long idCard,
        String cpf,
        String address,
        BigDecimal cardLimit) {
}
