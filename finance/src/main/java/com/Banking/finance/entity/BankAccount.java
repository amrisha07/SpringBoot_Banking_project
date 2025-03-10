package com.Banking.finance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BankAccount{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long bankAccount_id;

	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	@Column(name = "AMOUNT")
	private long amount;
	
	@Column(name = "BANK_ID")
	private long bankId;
	
	@Column(name = "BANK_NAME")
	private String bankName;

	@CreatedDate
	private Date bankAccountCreatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID")
	private Customer customer;

	@Enumerated(EnumType.STRING)
	private BankAccountStatus accountStatus;

	@Enumerated(EnumType.STRING)
	private BankAccountType accountType;

	@ManyToMany
	private List<Transaction> transactionsHistory;

}
