package io.github.techbank.creditcardservice.infra.mqueue;

public record DataSolicitationCardQDTO(
        Long idCard,
        String cpf,
        String address,
        Double cardLimit
) {

}