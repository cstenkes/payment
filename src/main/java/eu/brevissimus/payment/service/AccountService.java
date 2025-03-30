package eu.brevissimus.payment.service;

import eu.brevissimus.payment.exception.NotFoundException;
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

import static eu.brevissimus.payment.exception.ErrorCode.ACCOUNT_NOT_FOUND;
import static eu.brevissimus.payment.exception.ErrorCode.CUSTOMER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    public AccountBalanceDto getBalanceOfAccount(String accountNumber)  {
        return AccountBalanceDto.of(
                accountRepository.findByAccountNumber(accountNumber)
                        .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + accountNumber))
        );
    }

    public AccountBalanceDto getBalanceOfCard(String cardNumber)  {
        return AccountBalanceDto.of(
                cardRepository.findAccountByCardNumber(cardNumber)
                        .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "cardNumber: " + cardNumber))
        );
    }

    public List<AccountBalanceDto> getAllBalanceOfCustomer(String firstName, String lastName) {
        Customer customer = customerRepository.findByFirstNameAndLastName(firstName,lastName)
                        .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND, "firstName: " + firstName + ", lastName:"+lastName));
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

        Customer customer = customerRepository.findByFirstNameAndLastName(accountDto.firstName(), accountDto.lastName())
        .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "firstName: " + accountDto.firstName() + ", lastName:"+accountDto.lastName()));

        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    // can be modified account type and currency only
    public Account modfiyAccount(AccountDto accountDto) {
        Account account = accountRepository.findByAccountNumber(accountDto.accountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + accountDto.accountNumber()));
        account.setAccountType(accountDto.accountType());
        account.setCurrency(accountDto.currency());
        return accountRepository.save(account);
    }

    //logical delete
    @Transactional
    public Account deleteAccount(AccountDto accountDto) {
        Account account = accountRepository.findByAccountNumber(accountDto.accountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + accountDto.accountNumber()));
        account.setActive(false);
        return accountRepository.save(account);
    }

}
