package eu.brevissimus.payment.repository;

import eu.brevissimus.payment.model.entity.Account;
import eu.brevissimus.payment.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(value = "select c.account from Card c " +
            "where c.cardNumber = :cardNumber")
    Optional<Account> findAccountByCardNumber(String cardNumber);

    Optional<Card> findByCardNumber(String cardNumber);

}
