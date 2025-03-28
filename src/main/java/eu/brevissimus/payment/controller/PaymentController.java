package eu.brevissimus.payment.controller;

import eu.brevissimus.payment.model.dto.AccountBalanceDto;
import eu.brevissimus.payment.model.dto.AccountMoneyTransferDto;
import eu.brevissimus.payment.model.entity.Transaction;
import eu.brevissimus.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("payment/api")
@RestController
@RequiredArgsConstructor
@Tag(name = "payment", description = "all payment endpoints")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/accounts/{accountNumber}/transactions")
    public List<Transaction> getAllTransactionsOfAccount(@PathVariable String accountNumber) {
        List<Transaction> transactions = paymentService.getAllTransactionsByAccountNumber(accountNumber);
        log.info("all transactions of account {} : {}", accountNumber, transactions );
        return transactions;
    }

    @GetMapping("/cards/{cardNumber}/transactions")
    public List<Transaction> getAllTransactionsOfCard(@PathVariable String cardNumber) {
        List<Transaction> transactions = paymentService.getAllTransactionsByCardNumber(cardNumber);
        log.info("all transactions of card {} : {}", cardNumber, transactions );
        return transactions;
    }

    @GetMapping("/accounts/{accountNumber}/balance")
    public AccountBalanceDto getBalanceOfAccount(@PathVariable String accountNumber) {
        AccountBalanceDto sum = paymentService.getBalanceOfAccount(accountNumber);
        log.info("balance of account {} : {}", accountNumber, sum );
        return sum;
    }

    @GetMapping("/cards/{cardNumber}/balance")
    public AccountBalanceDto getBalanceOfCard(@PathVariable String cardNumber) {
        AccountBalanceDto sum = paymentService.getBalanceOfCard(cardNumber);
        log.info("balance of card {} : {}", cardNumber, sum );
        return sum;
    }

    @GetMapping("/customers/balance")
    public List<AccountBalanceDto> getAllBalanceOfCustomer(@RequestParam("fname") String firstName, @RequestParam("lname") String lastName) {
        List<AccountBalanceDto> accountBalances = paymentService.getAllBalanceOfCustomer(firstName, lastName);
        log.info("balance of customer {} {}: {}", firstName, lastName, accountBalances );
        return accountBalances;
    }

    // need to specify 2 accounts
    @PostMapping("/accounts/transfer/account2account")
    public List<Transaction> transferMoneyByAccountToAccount(@RequestBody AccountMoneyTransferDto transfer) {
        String cardNumber = null;
        List<Transaction> transactions = paymentService.getAllTransactionsByCardNumber(cardNumber);
        log.info("all transactions of card {} : {}", cardNumber, transactions );
        return transactions;
    }

    // need to specify card,account
    @PostMapping("/cards/transfer/card2account")
    public List<Transaction> transferMoneyByCardToAccount() {
        String cardNumber = null;
        List<Transaction> transactions = paymentService.getAllTransactionsByCardNumber(cardNumber);
        log.info("all transactions of card {} : {}", cardNumber, transactions );
        return transactions;
    }
}