package io.github.techbank.creditappraiserservice.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import io.github.techbank.creditappraiserservice.clients.creditcard.CreditCardClient;
import io.github.techbank.creditappraiserservice.clients.customer.CustomerClient;
import io.github.techbank.creditappraiserservice.dtos.CardApprovedDTO;
import io.github.techbank.creditappraiserservice.exceptions.PublisherException;
import io.github.techbank.creditappraiserservice.infra.mqueue.CardEmitPublisher;
import io.github.techbank.creditappraiserservice.infra.mqueue.DataSolicitationCardQDTO;
import io.github.techbank.creditappraiserservice.services.CreditAppraiserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class CreditAppraiserServiceImpl implements CreditAppraiserService {


    private final CreditCardClient creditCardClient;
    private final CustomerClient customerClient;
    private final CardEmitPublisher cardEmitPublisher;

    @Override
    public List<CardApprovedDTO> evaluate(String cpf, BigDecimal income) {
        if (cpf == null || cpf.isEmpty() || income == null) throw new IllegalArgumentException("Invalid object.");

        try {
            var customer = customerClient.getCustomerByCpf(cpf);
            if (customer.getBody() != null && customer.getBody().age() != null) {
                var creditCardListResponse = creditCardClient.getAllCardByIncome(income);
                if (creditCardListResponse != null && creditCardListResponse.getBody() != null) {
                    return creditCardListResponse.getBody()
                            .stream()
                            .map(c -> {
                                BigDecimal age = new BigDecimal(customer.getBody().age());
                                var approvedLimit = age.divide(new BigDecimal(10)).multiply(c.cardLimit());
                                return new CardApprovedDTO(c.name(), c.brand(), approvedLimit);
                            })
                            .toList();
                }
            }
        } catch (FeignException.FeignClientException e) {
            log.error("An unexpected error occurred: ", e);
        }

        return new ArrayList<>();
    }

    @Override
    public String requestCard(DataSolicitationCardQDTO data) {
        try {
            boolean requested = cardEmitPublisher.requestCard(data);
            if (requested)
                return UUID.randomUUID().toString();
            else
                throw new PublisherException("It not possible to send to queue.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
