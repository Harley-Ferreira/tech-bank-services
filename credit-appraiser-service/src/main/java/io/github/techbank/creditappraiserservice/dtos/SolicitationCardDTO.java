package io.github.techbank.creditappraiserservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SolicitationCardDTO(

        @NotNull
        Long idCard,

        @NotBlank
        String cpf,
        String address,
        BigDecimal cardLimit) {
}
