package io.github.techbank.creditcardservice.controllers;

import io.github.techbank.creditcardservice.dtos.CardCustomerDTO;
import io.github.techbank.creditcardservice.entities.CardCustomer;
import io.github.techbank.creditcardservice.services.CardCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit-card/customer")
public class CardCustomerController {

    private final CardCustomerService cardCustomerService;

    @GetMapping("{cpf}")
    public ResponseEntity<List<CardCustomerDTO>> getAllCardByCpf(@PathVariable("cpf") String cpf) {
        List<CardCustomer> cardCustomerList = cardCustomerService.getListCreditCardByCpf(cpf);
        List<CardCustomerDTO> list = cardCustomerList.stream().map(CardCustomerDTO::new).toList();
        return ResponseEntity.ok(list);
    }
}
