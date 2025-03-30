package eu.brevissimus.payment.model.dto;

import eu.brevissimus.payment.model.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountBalanceDto(
        @Schema(description = "Issue Date", example = "2025-02-12 12:30") LocalDateTime issueDate,
        @Schema(description = "Account Number", example = "12345678A") String accountNumber,
        @Schema(description = "Balance value", example = "1000") BigDecimal balance,
        @Schema(description = "Currency", example = "CHF") String currency) {

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
