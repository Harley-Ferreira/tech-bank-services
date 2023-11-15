package io.github.techbank.customerservice.repositories;

import io.github.techbank.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByCpf(String cpf);

    Optional<Customer> findByCpf(String cpf);
}
