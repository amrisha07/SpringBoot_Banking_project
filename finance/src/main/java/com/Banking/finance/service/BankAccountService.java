package com.Banking.finance.service;

import java.util.List;

import com.Banking.finance.entity.BankAccount;

public interface BankAccountService {

	// Save BankDetails
	List<BankAccount> saveBankDetails(List<BankAccount> bankAccount, Long customerId);

	// Read Customer Bank Account List
	List<BankAccount> fetchBankAccountList(Long customerId);

	// Update Customer Bank Account
	BankAccount updateBankAccount(BankAccount bankAccount, Long customerId);

	// Delete Customer Bank Account
	void deleteBankAccountById(Long customerId, Long bankId);
	
	long getAmount(Long customerId, Long bankId);

}
