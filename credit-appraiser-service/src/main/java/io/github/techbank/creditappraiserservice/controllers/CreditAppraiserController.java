package io.github.techbank.creditappraiserservice.controllers;

import io.github.techbank.creditappraiserservice.dtos.CardApprovedDTO;
import io.github.techbank.creditappraiserservice.dtos.SolicitationCardDTO;
import io.github.techbank.creditappraiserservice.infra.mqueue.DataSolicitationCardQDTO;
import io.github.techbank.creditappraiserservice.services.CreditAppraiserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("request-card")
    public ResponseEntity<String> requestCard(@RequestBody @Valid SolicitationCardDTO dto) {
        var data = new DataSolicitationCardQDTO(dto);
        return ResponseEntity.ok(creditAppraiserService.requestCard(data));
    }
}
