package eu.brevissimus.payment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AccountDto(
        @Schema(description = "Account Number", example = "12345678C") String accountNumber,
        @Schema(description = "Account Type", example = "debit") String accountType,
        @Schema(description = "Currency", example = "CHF") String currency,
        @Schema(description = "First Name", example = "Joch") String firstName,
        @Schema(description = "Last Name", example = "Deo") String lastName
) {
    // balance is 0 when creating
}
