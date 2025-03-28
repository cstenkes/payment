package eu.brevissimus.payment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record CardDto(
        @Schema(description = "Account Number", example = "12345678C") String accountNumber,
        @Schema(description = "First Name", example = "Joch") String firstName,
        @Schema(description = "Last Name", example = "Deo") String lastName,
        @Schema(description = "Card Number", example = "Card Number") String cardNumber,
        @Schema(description = "CCV Code", example = "123") Integer ccvCode,
        @Schema(description = "Expiry Date", example = "01/26") LocalDate expiry
) {
}
