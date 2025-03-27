package eu.brevissimus.payment.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import java.util.Date;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@ToString()
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Auditable {
  @JsonIgnore
  @CreatedDate
  @Column(name = "created_date")
  private Date createdDate;

  @JsonIgnore
  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;

  @JsonIgnore
  @LastModifiedDate
  @Column(name = "modified_date")
  private Date modifiedDate;

  @JsonIgnore
  @LastModifiedBy
  @Column(name = "modified_by")
  private String modofiedBy ;
}
