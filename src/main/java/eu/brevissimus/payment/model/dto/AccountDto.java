package eu.brevissimus.payment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record AccountDto(
        @NotBlank
        @Size(max = 50)
        @Schema(description = "Account Number", requiredMode = REQUIRED, example = "12345678C")
        String accountNumber,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "Account Type", requiredMode = REQUIRED, example = "debit")
        String accountType,
        @NotBlank
        @Size(max = 3)
        @Schema(description = "Currency", requiredMode = REQUIRED, example = "CHF")
        String currency,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "First Name", requiredMode = REQUIRED, example = "John")
        String firstName,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "Last Name", requiredMode = REQUIRED, example = "Doe")
        String lastName,
        @NotBlank
        @Schema(description = "balance", requiredMode = REQUIRED, example = "125.7")
        BigDecimal balance
) {
    // balance is 0 when creating
}
