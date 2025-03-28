package eu.brevissimus.payment.service;

import eu.brevissimus.payment.model.dto.AccountBalanceDto;
import eu.brevissimus.payment.model.entity.Customer;
import eu.brevissimus.payment.model.entity.Transaction;
import eu.brevissimus.payment.repository.AccountRepository;
import eu.brevissimus.payment.repository.CardRepository;
import eu.brevissimus.payment.repository.CustomerRepository;
import eu.brevissimus.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

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
}