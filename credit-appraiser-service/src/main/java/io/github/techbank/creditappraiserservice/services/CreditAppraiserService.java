package io.github.techbank.creditappraiserservice.services;

import io.github.techbank.creditappraiserservice.dtos.CardApprovedDTO;
import io.github.techbank.creditappraiserservice.infra.mqueue.DataSolicitationCardQDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CreditAppraiserService {

    List<CardApprovedDTO> evaluate(String cpf, BigDecimal income);
    String requestCard(DataSolicitationCardQDTO data);
}
