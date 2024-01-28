package io.github.techbank.creditcardservice.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.techbank.creditcardservice.entities.CardCustomer;
import io.github.techbank.creditcardservice.repositories.CreditCardRepository;
import io.github.techbank.creditcardservice.services.CardCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class CardEmitSubscriber {

    private final CardCustomerService cardCustomerService;
    private final CreditCardRepository creditCardRepository;

    @RabbitListener(queues = "${mq.queues.credit-card-emit}")
    public void receivesIssuingRequest(@Payload String json) {
        try {
            var data = new ObjectMapper().readValue(json, DataSolicitationCardQDTO.class);
            if (data.idCard() != null) {
                var creditCard = creditCardRepository.findById(data.idCard()).orElseThrow();
                CardCustomer cardCustomer = new CardCustomer();
                cardCustomer.setCreditCard(creditCard);
                cardCustomer.setCpf(data.cpf());
                cardCustomer.setCardLimit(data.cardLimit());
                cardCustomerService.save(cardCustomer);
            } else {
                log.info("Request failed. There is no idCard.");
            }
        } catch (JsonProcessingException e) {
            log.error("Erro try receive an emission or card: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
