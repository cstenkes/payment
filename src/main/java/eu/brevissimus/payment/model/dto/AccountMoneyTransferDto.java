package eu.brevissimus.payment.model.dto;

import eu.brevissimus.payment.model.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountMoneyTransferDto(
        @Schema(description = "Issue Date", example = "2025-02-12 12:30") LocalDateTime issueDate,
        @Schema(description = "From Account Number", example = "12345678A") String fromAccountNumber,
        @Schema(description = "To Account Number", example = "12345678B") String toAccountNumber,
        @Schema(description = "Transfer amount", example = "1000") BigDecimal amount,
        @Schema(description = "Transfer to currency", example = "CHF") String currency) {

    public static AccountMoneyTransferDto of(Account fromAccount, Account toAccount, BigDecimal amount) {
        return new AccountMoneyTransferDto(
                LocalDateTime.now(),
                fromAccount.getAccountNumber(),
                toAccount.getAccountNumber(),
                amount,
                toAccount.getCurrency()
        );
    }

}
