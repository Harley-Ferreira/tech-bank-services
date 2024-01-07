package io.github.techbank.creditappraiserservice.services;

import io.github.techbank.creditappraiserservice.dtos.CardApprovedDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CreditAppraiserService {

    List<CardApprovedDTO> evaluate(String cpf, BigDecimal income);

}
