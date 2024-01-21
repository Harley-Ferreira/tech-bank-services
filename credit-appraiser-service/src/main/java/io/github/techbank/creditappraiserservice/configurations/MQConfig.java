package io.github.techbank.creditappraiserservice.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.credit-card-emit}")
    private String cardEmitQueue;

    @Bean
    public Queue cardEmitQueue() {
        return new Queue(cardEmitQueue, true);
    }
}
