package io.github.techbank.creditappraiserservice.clients.creditcard;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(value = "credit-card-service", path = "credit-card")
public interface CreditCardClient {


    @GetMapping("{income}")
    ResponseEntity<List<CreditCardResponse>> getAllCardByIncome(@PathVariable("income") BigDecimal income);
}
