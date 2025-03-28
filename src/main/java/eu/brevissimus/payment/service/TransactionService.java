package eu.brevissimus.payment.service;

import eu.brevissimus.payment.model.dto.AccountMoneyTransferDto;
import eu.brevissimus.payment.model.dto.CardMoneyTransferDto;
import eu.brevissimus.payment.model.entity.Account;
import eu.brevissimus.payment.model.entity.Transaction;
import eu.brevissimus.payment.repository.AccountRepository;
import eu.brevissimus.payment.repository.CardRepository;
import eu.brevissimus.payment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    public List<Transaction> getAllTransactionsByAccountNumber(String accountNumber)  {
        return transactionRepository.findTransactionsByAccountNumber(accountNumber);
    }

    public List<Transaction> getAllTransactionsByCardNumber(String cardNumber)  {
        return transactionRepository.findTransactionsByCardNumber(cardNumber);
    }

    @Transactional
    public Transaction transferAccountMoney(AccountMoneyTransferDto transfer) {
        // Step 1 - id - autoincrement
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(transfer.issueDate());
        Account fromAccount = accountRepository.findByAccountNumber(transfer.fromAccountNumber());
        Account toAccount = accountRepository.findByAccountNumber(transfer.toAccountNumber());
        transaction.setToAccount(fromAccount);
        transaction.setFromAccount(toAccount);
        transaction.setAmount(transfer.amount());
        transaction.setStatus("STARTED");
        transactionRepository.save(transaction);

        //step 2-3 should be handled together

        // Step 2 Update balance1
        fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.amount()));
        accountRepository.save(fromAccount);
        transaction.setStatus("A1_REMOVED");
        transactionRepository.save(transaction);

        // Step 3 Update balance2
        toAccount.setBalance(toAccount.getBalance().add(transfer.amount()));
        accountRepository.save(toAccount);
        transaction.setStatus("A2_ADDED");
        transactionRepository.save(transaction);

        // Step 4 Update transaction
        transaction.setStatus("FINISHED_SUCCESS");
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transferCardMoney(CardMoneyTransferDto transfer) {
        // Step 1 - id - autoincrement
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(transfer.issueDate());
        Account fromAccount = cardRepository.findAccountByCardNumber(transfer.fromCardNumber());
        Account toAccount = accountRepository.findByAccountNumber(transfer.toAccountNumber());
        transaction.setToAccount(fromAccount);
        transaction.setFromAccount(toAccount);
        transaction.setAmount(transfer.amount());
        transaction.setStatus("STARTED");
        transactionRepository.save(transaction);

        //step 2-3 should be handled together

        // Step 2 Update balance1
        fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.amount()));
        accountRepository.save(fromAccount);
        transaction.setStatus("A1_REMOVED");
        transactionRepository.save(transaction);

        // Step 3 Update balance2
        toAccount.setBalance(toAccount.getBalance().add(transfer.amount()));
        accountRepository.save(toAccount);
        transaction.setStatus("A2_ADDED");
        transactionRepository.save(transaction);

        // Step 4 Update transaction
        transaction.setStatus("FINISHED_SUCCESS");
        return transactionRepository.save(transaction);
    }
}
