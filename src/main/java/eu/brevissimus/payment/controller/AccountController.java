package eu.brevissimus.payment.controller;

import eu.brevissimus.payment.kafkaservice.PublishMessageService;
import eu.brevissimus.payment.model.dto.AccountBalanceDto;
import eu.brevissimus.payment.model.dto.AccountDto;
import eu.brevissimus.payment.model.dto.AccountMoneyTransferDto;
import eu.brevissimus.payment.model.dto.TransactionDto;
import eu.brevissimus.payment.model.entity.Account;
import eu.brevissimus.payment.model.entity.Transaction;
import eu.brevissimus.payment.service.AccountService;
import eu.brevissimus.payment.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/payment/api/v${application.version}/accounts")
@RestController
@RequiredArgsConstructor
@Tag(name = "Payment by account", description = "all account related endpoints")
public class AccountController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final PublishMessageService publishMessageService;

    @Operation(
            summary = "Get all transaction of account by account number",
            description = "Returns all transaction of account by account number")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Retrieved all transactions of account was successful")
            })
    @GetMapping("/{accountNumber}/transactions")
    public List<TransactionDto> getAllTransactionsOfAccount(@Parameter(description = "Account number", example = "12345678A")
                                                         @PathVariable String accountNumber) {
        List<Transaction> transactions = transactionService.getAllTransactionsByAccountNumber(accountNumber);
        log.info("all transactions of account {} : {}", accountNumber, transactions );
        return transactions.stream().map(TransactionDto::of).toList();
    }

    @Operation(
            summary = "Money transfer by account to account with the same currency",
            description = "Money transfer by account to account with the same currency")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account to Account money transfer was successful")
            })
    @PostMapping("/transfer")
    public void transferMoneyByAccountToAccount(@RequestBody AccountMoneyTransferDto transfer) {
        publishMessageService.sendTransactionStartMessage(transfer);
        log.info("money transfer transaction was done via account to account {} ", transfer );
    }

    @Operation(
            summary = "Get balance of account by account number",
            description = "Returns balance of account by account number")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Balance of account by account number was successful")
            })
    @GetMapping("/{accountNumber}/balance")
    public AccountBalanceDto getBalanceOfAccount(@Parameter(description = "Account number", example = "12345678A")
                                                 @PathVariable String accountNumber) {
        AccountBalanceDto accountBalanceDto = accountService.getBalanceOfAccount(accountNumber);
        log.info("balance of account {} : {}", accountNumber, accountBalanceDto );
        return accountBalanceDto;
    }

    // trivial controller methods:

    @Operation(
            summary = "Account creation for an existing customer",
            description = "Account creation for an existing customer")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account creation for an existing customer was successful")
            })
    @PostMapping("/")
    public Account createAccount(@RequestBody AccountDto accountDto) {
        Account account = accountService.createAccount(accountDto);
        log.info("account created: {}", account );
        return account;
    }

    @Operation(
            summary = "Existing Account modification",
            description = "Existing Account modification")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Existing Account modification was successful")
            })
    @PutMapping("/")
    public Account modifyAccount(@RequestBody AccountDto accountDto) {
        Account account = accountService.modfiyAccount(accountDto);
        log.info("account modified: {}", account );
        return account;
    }

    @Operation(
            summary = "Existing account deletion (only logical)",
            description = "Existing account deletion (only logical)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Existing account deletion (only logical) was successful")
            })
    @DeleteMapping("/")
    public Account deleteAccount(@RequestBody AccountDto accountDto) {
        Account account = accountService.deleteAccount(accountDto);
        log.info("account deleted: {}", account );
        return account;
    }
}
