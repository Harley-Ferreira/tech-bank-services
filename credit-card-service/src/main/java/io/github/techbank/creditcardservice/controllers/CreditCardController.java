package io.github.techbank.creditcardservice.controllers;

import io.github.techbank.creditcardservice.dtos.CreditCardDTO;
import io.github.techbank.creditcardservice.entities.CreditCard;
import io.github.techbank.creditcardservice.services.CreditCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("credit-card")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping
    public ResponseEntity<CreditCardDTO> register(@RequestBody @Valid CreditCardDTO creditCardDTO, UriComponentsBuilder uriComponentsBuilder) {
        var creditCard = new CreditCard(creditCardDTO);
        creditCard = creditCardService.register(creditCard);
        creditCardDTO = new CreditCardDTO(creditCard);
        var uri = uriComponentsBuilder.path("/credit-card/{id}").buildAndExpand(creditCard.getId()).toUri();
        return ResponseEntity.created(uri).body(creditCardDTO);
    }
}
