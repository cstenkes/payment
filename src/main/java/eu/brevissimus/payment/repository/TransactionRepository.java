package eu.brevissimus.payment.repository;

import eu.brevissimus.payment.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select t from Transaction t " +
            "left join fetch t.fromAccount a " +
            "left join fetch t.toAccount b where a.accountNumber = :accountNumber or b.accountNumber = :accountNumber")
    Optional<List<Transaction>> findTransactionsByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query(value = "select t from Transaction t " +
            "left join fetch t.fromAccount a " +
            "left join fetch t.toAccount b " +
            "where a.accountNumber = (select c.account.accountNumber from Card c where c.cardNumber= :cardNumber) " +
            "or b.accountNumber = (select c.account.accountNumber from Card c where c.cardNumber= :cardNumber)")
    Optional<List<Transaction>> findTransactionsByCardNumber(@Param("cardNumber") String cardNumber);

}
