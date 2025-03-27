package eu.brevissimus.payment.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Transaction extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "transaction_date")
  private Date transactionDate;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "from_account_id", referencedColumnName = "id")
  private Account fromAccount;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "to_account_id", referencedColumnName = "id")
  private Account toAccount;

  @Column(name = "amount")
  private BigInteger amount;

  @Column(name = "status")
  private String status;
}
