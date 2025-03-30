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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static eu.brevissimus.payment.exception.ErrorCode.ACCOUNT_NOT_FOUND;
import static eu.brevissimus.payment.exception.ErrorCode.CARD_NOT_FOUND;

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
    public Transaction transferAccountMoney(AccountMoneyTransferDto transfer) {
        // Step 1 - id - autoincrement
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(transfer.issueDate());

        Account fromAccount = accountRepository.findByAccountNumber(transfer.fromAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + transfer.fromAccountNumber()));

        Account toAccount = accountRepository.findByAccountNumber(transfer.toAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + transfer.toAccountNumber()));

        transaction.setToAccount(fromAccount);
        transaction.setFromAccount(toAccount);
        transaction.setAmount(transfer.amount());
        transaction.setStatus(TransactionStatus.STARTED.name());
        transactionRepository.save(transaction);

        //step 2-3 should be handled together

        // Step 2 Update balance1
        fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.amount()));
        accountRepository.save(fromAccount);
        transaction.setStatus(TransactionStatus.A_1_REMOVED.name());
        transactionRepository.save(transaction);

        // Step 3 Update balance2
        toAccount.setBalance(toAccount.getBalance().add(transfer.amount()));
        accountRepository.save(toAccount);
        transaction.setStatus(TransactionStatus.A_2_ADDED.name());
        transactionRepository.save(transaction);

        // Step 4 Update transaction
        transaction.setStatus(TransactionStatus.FINISHED_SUCCESS.name());
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transferCardMoney(CardMoneyTransferDto transfer) {
        // Step 1 - id - autoincrement
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(transfer.issueDate());
        Account fromAccount = cardRepository.findAccountByCardNumber(transfer.fromCardNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "cardNumber: " + transfer.fromCardNumber()));
        Account toAccount = accountRepository.findByAccountNumber(transfer.toAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + transfer.toAccountNumber()));
        transaction.setToAccount(fromAccount);
        transaction.setFromAccount(toAccount);
        transaction.setAmount(transfer.amount());
        transaction.setStatus(TransactionStatus.STARTED.name());
        transactionRepository.save(transaction);

        //step 2-3 should be handled together

        // Step 2 Update balance1
        fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.amount()));
        accountRepository.save(fromAccount);
        transaction.setStatus(TransactionStatus.A_1_REMOVED.name());
        transactionRepository.save(transaction);

        // Step 3 Update balance2
        toAccount.setBalance(toAccount.getBalance().add(transfer.amount()));
        accountRepository.save(toAccount);
        transaction.setStatus(TransactionStatus.A_2_ADDED.name());
        transactionRepository.save(transaction);

        // Step 4 Update transaction
        transaction.setStatus(TransactionStatus.FINISHED_SUCCESS.name());
        return transactionRepository.save(transaction);
    }
}
