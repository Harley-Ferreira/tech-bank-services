package io.github.techbank.customerservice.entities;

import io.github.techbank.customerservice.dtos.CustomerDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotBlank
    @Column(name = "customer_name")
    private String name;

    @NotBlank
    @Column(length = 14)
    private String cpf;

    @Column
    private Integer age;

    public Customer(CustomerDTO customerDTO) {
        this.id = customerDTO.id();
        this.name = customerDTO.name();
        this.cpf = customerDTO.cpf();
        this.age = customerDTO.age();
    }
}
