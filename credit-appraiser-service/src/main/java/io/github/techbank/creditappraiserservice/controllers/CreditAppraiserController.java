package io.github.techbank.creditappraiserservice.controllers;

import io.github.techbank.creditappraiserservice.dtos.CardApprovedDTO;
import io.github.techbank.creditappraiserservice.services.CreditAppraiserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("credit-appraiser")
public class CreditAppraiserController {

    private final CreditAppraiserService creditAppraiserService;

    @GetMapping("make-evaluation/{cpf}/{income}")
    public ResponseEntity<List<CardApprovedDTO>> makeEvaluation(@PathVariable("cpf") String cpf,
                                                                @PathVariable("income") BigDecimal income) {
        var cards = creditAppraiserService.evaluate(cpf, income);
        return ResponseEntity.ok(cards);
    }
}
