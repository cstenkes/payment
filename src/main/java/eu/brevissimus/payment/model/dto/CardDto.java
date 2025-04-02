package eu.brevissimus.payment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CardDto(
        @NotBlank
        @Size(max = 50)
        @Schema(description = "Account Number", requiredMode = REQUIRED, example = "12345678C")
        String accountNumber,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "First Name", requiredMode = REQUIRED, example = "Joch")
        String firstName,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "Last Name", requiredMode = REQUIRED, example = "Deo")
        String lastName,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "Card Number", requiredMode = REQUIRED, example = "Card Number")
        String cardNumber,
        @NotBlank
        @Schema(description = "CCV Code", requiredMode = REQUIRED, example = "123")
        Integer ccvCode,
        @NotNull
        @PositiveOrZero
        @Schema(description = "Expiry Date", requiredMode = REQUIRED, example = "01/26")
        LocalDate expiry
) {
}
