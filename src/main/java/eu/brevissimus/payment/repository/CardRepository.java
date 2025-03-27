package eu.brevissimus.payment.repository;

import eu.brevissimus.payment.model.entity.Account;
import eu.brevissimus.payment.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(value = "select c.account from Card c " +
            "where c.cardNumber = :cardNumber")
    Account findAccountByCardNumber(String cardNumber);
}
