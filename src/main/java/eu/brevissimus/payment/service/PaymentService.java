package eu.brevissimus.payment.service;

import eu.brevissimus.payment.model.dto.AccountBalanceDto;
import eu.brevissimus.payment.model.entity.Transaction;
import eu.brevissimus.payment.repository.AccountRepository;
import eu.brevissimus.payment.repository.CardRepository;
import eu.brevissimus.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

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

}