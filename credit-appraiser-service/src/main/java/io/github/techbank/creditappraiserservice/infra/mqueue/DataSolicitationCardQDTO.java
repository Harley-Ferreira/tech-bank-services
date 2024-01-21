package io.github.techbank.creditappraiserservice.infra.mqueue;

import io.github.techbank.creditappraiserservice.dtos.SolicitationCardDTO;

import java.math.BigDecimal;

public record DataSolicitationCardQDTO(
        Long idCard,
        String cpf,
        String address,
        BigDecimal cardLimit
) {
    public DataSolicitationCardQDTO(SolicitationCardDTO dto) {
        this(
                dto.idCard(),
                dto.cpf(),
                dto.address(),
                dto.cardLimit()
        );
    }
}