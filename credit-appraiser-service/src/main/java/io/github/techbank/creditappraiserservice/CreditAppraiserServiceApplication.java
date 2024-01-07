package io.github.techbank.creditappraiserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CreditAppraiserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditAppraiserServiceApplication.class, args);
	}

}
