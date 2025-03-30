package eu.brevissimus.payment.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
  private LocalDateTime transactionDate;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "from_account_id", referencedColumnName = "id")
  private Account fromAccount;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "to_account_id", referencedColumnName = "id")
  private Account toAccount;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "status")
  private TransactionStatus status;
}
