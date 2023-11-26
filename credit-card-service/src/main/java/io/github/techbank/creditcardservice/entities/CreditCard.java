package io.github.techbank.creditcardservice.entities;

import io.github.techbank.creditcardservice.dtos.CreditCardDTO;
import io.github.techbank.creditcardservice.enums.CardBrand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "credit_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "card_flag")
    @Enumerated(EnumType.STRING)
    private CardBrand cardBrand;

    @NotNull
    @Column(name = "income")
    private Double income;

    @NotNull
    @Column(name = "cardLimit")
    private BigDecimal cardLimit;

    public CreditCard(CreditCardDTO creditCardDTO) {
        this.id = creditCardDTO.id();
        this.name = creditCardDTO.name();
        this.cardBrand = CardBrand.valueOf(creditCardDTO.brand());
        this.income = creditCardDTO.income();
        this.cardLimit = creditCardDTO.cardLimit();
    }
}

