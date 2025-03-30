package eu.brevissimus.payment.model.dto;

import eu.brevissimus.payment.model.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record AccountBalanceDto(
        @NotNull
        @Schema(description = "Issue Date", requiredMode = REQUIRED, example = "2025-02-12 12:30")
        LocalDateTime issueDate,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "Account Number", requiredMode = REQUIRED, example = "12345678A")
        String accountNumber,
        @NotBlank
        @Schema(description = "Balance value", requiredMode = REQUIRED, example = "1000")
        BigDecimal balance,
        @NotBlank
        @Size(max = 3)
        @Schema(description = "Currency", requiredMode = REQUIRED, example = "CHF")
        String currency) {

    public static AccountBalanceDto of(Account account) {
        return new AccountBalanceDto(
                LocalDateTime.now(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getCurrency()
        );
    }

    public static AccountBalanceDto of(Account account, LocalDateTime now) {
        return new AccountBalanceDto(
                now,
                account.getAccountNumber(),
                account.getBalance(),
                account.getCurrency()
        );
    }

}
