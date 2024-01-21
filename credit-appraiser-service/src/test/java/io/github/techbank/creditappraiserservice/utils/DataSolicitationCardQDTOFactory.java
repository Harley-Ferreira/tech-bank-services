package io.github.techbank.creditappraiserservice.utils;

import io.github.techbank.creditappraiserservice.infra.mqueue.DataSolicitationCardQDTO;

import java.math.BigDecimal;

public class DataSolicitationCardQDTOFactory {

    public static DataSolicitationCardQDTO createNewDataSolicitationCardQDTO() {
        return new DataSolicitationCardQDTO( 1L, "123.456.789-00", "Rua X", new BigDecimal("10000.00"));
    }
}
