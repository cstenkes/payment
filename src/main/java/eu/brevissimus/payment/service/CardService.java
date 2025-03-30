package eu.brevissimus.payment.service;

import eu.brevissimus.payment.exception.NotFoundException;
import eu.brevissimus.payment.model.dto.CardDto;
import eu.brevissimus.payment.model.entity.Account;
import eu.brevissimus.payment.model.entity.Card;
import eu.brevissimus.payment.model.entity.Customer;
import eu.brevissimus.payment.repository.AccountRepository;
import eu.brevissimus.payment.repository.CardRepository;
import eu.brevissimus.payment.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static eu.brevissimus.payment.exception.ErrorCode.ACCOUNT_NOT_FOUND;
import static eu.brevissimus.payment.exception.ErrorCode.CARD_NOT_FOUND;
import static eu.brevissimus.payment.exception.ErrorCode.CUSTOMER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CardService {
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public Card createCard(CardDto cardDto) {
        Card card = new Card();
        card.setCardNumber(cardDto.cardNumber());
        card.setCcvCode(cardDto.ccvCode());
        card.setExpiry(cardDto.expiry());
        Customer customer = customerRepository.findByFirstNameAndLastName(cardDto.firstName(),cardDto.lastName())
                .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND, "firstName: " + cardDto.firstName() +
                        ", lastName:" + cardDto.lastName()));
        card.setCustomer(customer);
        Account account = accountRepository.findByAccountNumber(cardDto.accountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND, "Account number: " + cardDto.accountNumber()));
        card.setAccount(account);
        return cardRepository.save(card);
    }

    // can be modified ccv, expiry date only
    public Card modifyCard(CardDto cardDto) {
        Card card = cardRepository.findByCardNumber(cardDto.cardNumber())
                .orElseThrow(() -> new NotFoundException(CARD_NOT_FOUND, "Card number: " + cardDto.cardNumber()));
        card.setCcvCode(cardDto.ccvCode());
        card.setExpiry(cardDto.expiry());
        return cardRepository.save(card);
    }

    // logical deletions
    @Transactional
    public Card deleteCard(CardDto cardDto) {
        Card card = cardRepository.findByCardNumber(cardDto.cardNumber())
                .orElseThrow(() -> new NotFoundException(CARD_NOT_FOUND, "Card number: " + cardDto.cardNumber()));
        card.setActive(false);
        return cardRepository.save(card);
    }
}
