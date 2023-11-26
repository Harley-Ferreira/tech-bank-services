package io.github.techbank.creditcardservice.repositories;

import io.github.techbank.creditcardservice.entities.CardCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CardCustomerRepository extends JpaRepository<CardCustomer, Long> {
    List<CardCustomer> findAllByCpf(String cpf);
}
