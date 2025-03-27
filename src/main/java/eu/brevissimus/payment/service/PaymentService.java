package eu.brevissimus.payment.service;

import eu.brevissimus.payment.model.Transaction;
import eu.brevissimus.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@Service
public class PaymentService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactionsByAccountNumber(String accountNumber)  {
        return transactionRepository.findTransactionsByAccountNumber(accountNumber);
    }

    public List<Transaction> getAllTransactionsByCardNumber(String cardNumber)  {
        return transactionRepository.findTransactionsByCardNumber(cardNumber);
    }

}