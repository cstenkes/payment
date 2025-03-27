package eu.brevissimus.payment.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigInteger;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Account extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "account_number")
  private String accountNumber;

  @Column(name = "account_type")
  private String accountType;

  @Column(name = "currency")
  private String currency;

  @Column(name = "balance")
  private BigInteger balance;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  Customer customer;

  @ToString.Exclude
  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "card_id", referencedColumnName = "id")
  private Card card;

  @ToString.Exclude
  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "transaction_id", referencedColumnName = "id")
  private List<Transaction> transactions;

}
