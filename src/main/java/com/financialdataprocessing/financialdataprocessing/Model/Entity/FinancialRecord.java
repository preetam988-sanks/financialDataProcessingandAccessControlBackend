package com.financialdataprocessing.financialdataprocessing.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialdataprocessing.financialdataprocessing.Model.Enum.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Table(name="financial_records",indexes={
    @Index(name="idx_user_id", columnList = "user_id"),
    @Index(name="idx_entry_date", columnList = "entryDate"),
    @Index(name="idx_category", columnList = "category"),
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class FinancialRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable=false)
    private String category;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;


    @Column(nullable=false,updatable = false)
    private java.time.LocalDate entryDate;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private java.time.LocalDateTime createdAt;
    @LastModifiedDate
    private java.time.LocalDateTime updatedAt;
    @Column
    private String description;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
    private boolean deleted=false;
}
