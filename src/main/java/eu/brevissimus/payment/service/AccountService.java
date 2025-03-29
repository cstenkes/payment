package eu.brevissimus.payment.service;

import eu.brevissimus.payment.model.dto.AccountBalanceDto;
import eu.brevissimus.payment.model.dto.AccountDto;
import eu.brevissimus.payment.model.entity.Account;
import eu.brevissimus.payment.model.entity.Customer;
import eu.brevissimus.payment.repository.AccountRepository;
import eu.brevissimus.payment.repository.CardRepository;
import eu.brevissimus.payment.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

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
    public Account createAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setAccountNumber(accountDto.accountNumber());
        account.setAccountType(accountDto.accountType());
        account.setCurrency(accountDto.currency());

        Customer customer = customerRepository.findByFirstNameAndLastName(accountDto.firstName(),accountDto.lastName());
        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    // can be modified account type and currency only
    public Account modfiyAccount(AccountDto accountDto) {
        Account account = accountRepository.findByAccountNumber(accountDto.accountNumber());
        account.setAccountType(accountDto.accountType());
        account.setCurrency(accountDto.currency());
        return accountRepository.save(account);
    }

    //logical delete
    @Transactional
    public Account deleteAccount(AccountDto accountDto) {
        Account account = accountRepository.findByAccountNumber(accountDto.accountNumber());
        account.setActive(false);
        return accountRepository.save(account);
    }

}
