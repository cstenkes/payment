package eu.brevissimus.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Customer extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "address")
  private String address;

  @ToString.Exclude
  @JsonIgnore
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Account> accounts = new ArrayList<>();

  @ToString.Exclude
  @JsonIgnore
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Card> cards = new ArrayList<>();

}
