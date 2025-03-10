package com.Banking.finance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;

    @JoinColumn(name = "customer_id")
    private long sourceCustomerId;

    @JoinColumn(name = "customer_id")
    private long destinationCustomerId;

    @JoinColumn(name = "bankAccount_id")
    private long sourceBankId;

    @JoinColumn(name = "bankAccount_id")
    private long destinationBankId;

    @NotNull
    private long transactionAmount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @CreatedDate
    private Date transactionDate;
}
