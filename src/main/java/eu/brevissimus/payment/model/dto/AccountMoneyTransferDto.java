package eu.brevissimus.payment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record AccountMoneyTransferDto(
        @NotNull
        @Schema(description = "Issue Date", requiredMode = REQUIRED, example = "2025-02-12 12:30")
        LocalDateTime issueDate,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "From Account Number", requiredMode = REQUIRED, example = "12345678A")
        String fromAccountNumber,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "To Account Number", requiredMode = REQUIRED, example = "12345678B")
        String toAccountNumber,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "Transfer amount", requiredMode = REQUIRED, example = "1000")
        BigDecimal amount,
        @NotBlank
        @Size(max = 3)
        @Schema(description = "Transfer to currency", requiredMode = REQUIRED, example = "CHF")
        String currency) {

}
