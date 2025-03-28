package eu.brevissimus.payment.controller;

import eu.brevissimus.payment.model.dto.AccountBalanceDto;
import eu.brevissimus.payment.model.dto.AccountDto;
import eu.brevissimus.payment.model.dto.AccountMoneyTransferDto;
import eu.brevissimus.payment.model.dto.CardDto;
import eu.brevissimus.payment.model.dto.CardMoneyTransferDto;
import eu.brevissimus.payment.model.dto.CustomerDto;
import eu.brevissimus.payment.model.entity.Account;
import eu.brevissimus.payment.model.entity.Card;
import eu.brevissimus.payment.model.entity.Customer;
import eu.brevissimus.payment.model.entity.Transaction;
import eu.brevissimus.payment.service.AccountService;
import eu.brevissimus.payment.service.CardService;
import eu.brevissimus.payment.service.CustomerService;
import eu.brevissimus.payment.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/payment/api/v${application.version}")
@RestController
@RequiredArgsConstructor
@Tag(name = "payment", description = "all payment endpoints")
public class PaymentController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CustomerService customerService;
    private final CardService cardService;

    @GetMapping("/accounts/{accountNumber}/transactions")
    public List<Transaction> getAllTransactionsOfAccount(@PathVariable String accountNumber) {
        List<Transaction> transactions = transactionService.getAllTransactionsByAccountNumber(accountNumber);
        log.info("all transactions of account {} : {}", accountNumber, transactions );
        return transactions;
    }

    @GetMapping("/cards/{cardNumber}/transactions")
    public List<Transaction> getAllTransactionsOfCard(@PathVariable String cardNumber) {
        List<Transaction> transactions = transactionService.getAllTransactionsByCardNumber(cardNumber);
        log.info("all transactions of card {} : {}", cardNumber, transactions );
        return transactions;
    }

    @GetMapping("/accounts/{accountNumber}/balance")
    public AccountBalanceDto getBalanceOfAccount(@PathVariable String accountNumber) {
        AccountBalanceDto accountBalance = accountService.getBalanceOfAccount(accountNumber);
        log.info("balance of account {} : {}", accountNumber, accountBalance );
        return accountBalance;
    }

    @GetMapping("/cards/{cardNumber}/balance")
    public AccountBalanceDto getBalanceOfCard(@PathVariable String cardNumber) {
        AccountBalanceDto accountBalance = accountService.getBalanceOfCard(cardNumber);
        log.info("balance of card {} : {}", cardNumber, accountBalance );
        return accountBalance;
    }

    @GetMapping("/customers/balance")
    public List<AccountBalanceDto> getAllBalanceOfCustomer(@RequestParam("fname") String firstName, @RequestParam("lname") String lastName) {
        List<AccountBalanceDto> accountBalances = accountService.getAllBalanceOfCustomer(firstName, lastName);
        log.info("balance of customer {} {}: {}", firstName, lastName, accountBalances );
        return accountBalances;
    }

    @PostMapping("/accounts/transfer/account2account")
    public Transaction transferMoneyByAccountToAccount(@RequestBody AccountMoneyTransferDto transfer) {
        Transaction transaction = transactionService.transferAccountMoney(transfer);
        log.info("money fransfer transaction was done via acount to account {} : {}", transfer, transaction );
        return transaction;
    }

    @PostMapping("/cards/transfer/card2account")
    public Transaction transferMoneyByCardToAccount(@RequestBody CardMoneyTransferDto transfer) {
        Transaction transaction = transactionService.transferCardMoney(transfer);
        log.info("money fransfer transaction was done via card to account {} : {}", transfer, transaction );
        return transaction;
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.createCustomer(customerDto);
        log.info("customer created: {}", customer );
        return customer;
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody AccountDto accountDto) {
        Account account = accountService.createAccount(accountDto);
        log.info("account created: {}", account );
        return account;
    }

    @PostMapping("/cards")
    public Card createCard(@RequestBody CardDto cardDto) {
        Card card = cardService.createCard(cardDto);
        log.info("card created: {}", card );
        return card;
    }


    @DeleteMapping("/customers")
    public Customer deleteCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.deleteCustomer(customerDto);
        log.info("customer deleted: {}", customer );
        return customer;
    }

    @DeleteMapping("/accounts")
    public Account deleteAccount(@RequestBody AccountDto accountDto) {
        Account account = accountService.deleteAccount(accountDto);
        log.info("account deleted: {}", account );
        return account;
    }

    @DeleteMapping("/cards")
    public Card deleteCard(@RequestBody CardDto cardDto) {
        Card card = cardService.deleteCard(cardDto);
        log.info("card deleted: {}", card );
        return card;
    }

}