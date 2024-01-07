package io.github.techbank.creditappraiserservice.dtos;

import java.math.BigDecimal;

public record CardApprovedDTO(
        String cardName,
        String cardBrand,
        BigDecimal approvedCardLimit
) {


}
