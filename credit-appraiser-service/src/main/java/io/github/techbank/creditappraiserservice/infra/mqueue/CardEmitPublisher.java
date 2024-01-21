package io.github.techbank.creditappraiserservice.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class CardEmitPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue emitCardQueue;

    public boolean requestCard(DataSolicitationCardQDTO data) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(data);
        rabbitTemplate.convertAndSend(emitCardQueue.getName(), json);
        return true;
    }

}
