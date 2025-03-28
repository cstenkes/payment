package eu.brevissimus.payment.service;

import eu.brevissimus.payment.model.dto.AccountBalanceDto;
import eu.brevissimus.payment.model.dto.AccountMoneyTransferDto;
import eu.brevissimus.payment.model.dto.CardMoneyTransferDto;
import eu.brevissimus.payment.model.entity.Account;
import eu.brevissimus.payment.model.entity.Customer;
import eu.brevissimus.payment.model.entity.Transaction;
import eu.brevissimus.payment.repository.AccountRepository;
import eu.brevissimus.payment.repository.CardRepository;
import eu.brevissimus.payment.repository.CustomerRepository;
import eu.brevissimus.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    public List<Transaction> getAllTransactionsByAccountNumber(String accountNumber)  {
        return transactionRepository.findTransactionsByAccountNumber(accountNumber);
    }

    public List<Transaction> getAllTransactionsByCardNumber(String cardNumber)  {
        return transactionRepository.findTransactionsByCardNumber(cardNumber);
    }

    public AccountBalanceDto getBalanceOfAccount(String accountNumber)  {
        return AccountBalanceDto.of(accountRepository.findByAccountNumber(accountNumber));
    }

    public AccountBalanceDto getBalanceOfCard(String cardNumber)  {
        return AccountBalanceDto.of(cardRepository.findAccountByCardNumber(cardNumber));
    }

    public List<AccountBalanceDto> getAllBalanceOfCustomer(String firstName, String lastName) {
        Customer customer = customerRepository.findByFirstNameAndLastName(firstName,lastName);
        LocalDateTime now = LocalDateTime.now();
        return customer.getAccounts().stream()
                .map(a -> AccountBalanceDto.of(a, now))
                .collect(Collectors.toList());
    }


    @Transactional
    public Transaction doAccountMoneyTransfer(AccountMoneyTransferDto transfer) {
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
        transactionRepository.save(transaction);
        return transaction;
    }

    @Transactional
    public Transaction doCardMoneyTransfer(CardMoneyTransferDto transfer) {
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
        transactionRepository.save(transaction);
        return transaction;
    }
}