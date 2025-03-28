package eu.brevissimus.payment.service;

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

    @Transactional
    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.firstName());
        customer.setLastName(customerDto.lastName());
        customer.setAddress(customerDto.address());
        return customerRepository.save(customer);
    }

    @Transactional
    public Account createAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setAccountNumber(accountDto.accountNumber());
        account.setAccountType(accountDto.accountType());
        account.setCurrency(accountDto.currency());

        Customer customer = customerRepository.findByFirstNameAndLastName(accountDto.firstName(),accountDto.lastName());
        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    @Transactional
    public Card createCard(CardDto cardDto) {
        Card card = new Card();
        card.setCardNumber(cardDto.cardNumber());
        card.setCcvCode(cardDto.ccvCode());
        card.setExpiry(cardDto.expiry());
        Customer customer = customerRepository.findByFirstNameAndLastName(cardDto.firstName(),cardDto.lastName());
        card.setCustomer(customer);
        Account account = accountRepository.findByAccountNumber(cardDto.accountNumber());
        card.setAccount(account);
        return cardRepository.save(card);
    }

    // logical deletions

    @Transactional
    public Customer deleteCustomer(CustomerDto customerDto) {
        Customer customer = customerRepository.findByFirstNameAndLastName(customerDto.firstName(), customerDto.lastName());
        customer.setActive(false);
        return customerRepository.save(customer);
    }

    @Transactional
    public Account deleteAccount(AccountDto accountDto) {
        Account account = accountRepository.findByAccountNumber(accountDto.accountNumber());
        account.setActive(false);
        return accountRepository.save(account);
    }

    @Transactional
    public Card deleteCard(CardDto cardDto) {
        Card card = cardRepository.findByCardNumber(cardDto.cardNumber());
        card.setActive(false);
        return cardRepository.save(card);
    }


}