package eu.brevissimus.payment.service;

import eu.brevissimus.payment.exception.NotFoundException;
import eu.brevissimus.payment.model.dto.AccountMoneyTransferDto;
import eu.brevissimus.payment.model.dto.CardMoneyTransferDto;
import eu.brevissimus.payment.model.entity.Account;
import eu.brevissimus.payment.model.entity.Transaction;
import eu.brevissimus.payment.model.entity.TransactionStatus;
import eu.brevissimus.payment.repository.AccountRepository;
import eu.brevissimus.payment.repository.CardRepository;
import eu.brevissimus.payment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static eu.brevissimus.payment.exception.ErrorCode.ACCOUNT_NOT_FOUND;
import static eu.brevissimus.payment.exception.ErrorCode.CARD_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    public List<Transaction> getAllTransactionsByAccountNumber(String accountNumber)  {
        return transactionRepository.findTransactionsByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + accountNumber));
    }

    public List<Transaction> getAllTransactionsByCardNumber(String cardNumber)  {
        return transactionRepository.findTransactionsByCardNumber(cardNumber)
                .orElseThrow(() -> new NotFoundException(CARD_NOT_FOUND, "Card number: " + cardNumber));
    }

    @Transactional
    public Transaction transferAccountMoney1(AccountMoneyTransferDto transfer) {
        // preparation:
        Account fromAccount = accountRepository.findByAccountNumber(transfer.fromAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + transfer.fromAccountNumber()));

        Account toAccount = accountRepository.findByAccountNumber(transfer.toAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + transfer.toAccountNumber()));

        return createTransaction(transfer.amount(),transfer.issueDate(), fromAccount, toAccount);
    }

    @Transactional
    public Transaction transferCardMoney1(CardMoneyTransferDto transfer) {
        // preparation:
        Account fromAccount = cardRepository.findAccountByCardNumber(transfer.fromCardNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "cardNumber: " + transfer.fromCardNumber()));
        Account toAccount = accountRepository.findByAccountNumber(transfer.toAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + transfer.toAccountNumber()));

        return createTransaction(transfer.amount(),transfer.issueDate(), fromAccount, toAccount);
    }

    private Transaction createTransaction(BigDecimal amount, LocalDateTime issueDate, Account fromAccount, Account toAccount) {
        // create transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(issueDate);
        transaction.setToAccount(fromAccount);
        transaction.setFromAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.STARTED.name());
        transaction = transactionRepository.save(transaction);
        log.info("Transaction: {}",transaction);
        return transaction;
    }

    @Transactional
    public void transferMoney2(Long transactionId) {
        // Step 2
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()-> new NotFoundException(ACCOUNT_NOT_FOUND, "transaction Id: " + transactionId));
        transaction.setStatus(TransactionStatus.FINISHED.name());
        transaction = transactionRepository.save(transaction);
        log.info("Saved transaction: {}", transaction);

        Account fromAccount = transaction.getFromAccount();
        BigDecimal amount = transaction.getAmount();
        Account toAccount = transaction.getToAccount();

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountRepository.save(fromAccount);
        log.info("Saved from account: {}", fromAccount);

        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(toAccount);
        log.info("Saved to account: {}", toAccount);
    }

}