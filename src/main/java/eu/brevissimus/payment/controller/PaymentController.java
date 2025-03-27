package eu.brevissimus.payment.controller;

import eu.brevissimus.payment.model.Transaction;
import eu.brevissimus.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("payment/api")
@RestController
@RequiredArgsConstructor
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

    // need to specify 2 accounts
    @PostMapping("/accounts/transfer/account2account")
    public List<Transaction> transferMoneyByAccountToAccount() {
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