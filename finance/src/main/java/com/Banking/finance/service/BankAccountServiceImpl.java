package com.Banking.finance.service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Banking.finance.entity.BankAccount;
import com.Banking.finance.entity.Customer;
import com.Banking.finance.repository.BankAccountDao;
import com.Banking.finance.repository.CustomerDao;

@Service
public class BankAccountServiceImpl implements BankAccountService {
	
	Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);

	@Autowired
	BankAccountDao bankAccountDao;

	@Autowired
	private CustomerDao customerDao;

	@Override
	public List<BankAccount> saveBankDetails(List<BankAccount> bankAccount, Long customerId) {

		Optional<Customer> customerPresent = customerDao.findById(customerId);
		if (!customerPresent.isPresent()) {
			logger.info("No customer is present for adding bank details");
			return Collections.emptyList();
		}
		bankAccount.stream().forEach(account -> {
			account.setCustomer(account.getCustomer());
			bankAccountDao.save(account);
		});
		logger.info("Bank Account details for customer {} saved successfully" , customerPresent.get().getCustomerName());
		return bankAccount;
	}

	@Override
	public List<BankAccount> fetchBankAccountList(Long customerId) {
		Optional<Customer> customerById = customerDao.findById(customerId);
		if (!customerById.isPresent()) {
			return Collections.emptyList();
		}
		return customerById.get().getBankAccount();
	}

	@Override
	public BankAccount updateBankAccount(BankAccount bankAccount, Long customerId) {
		Optional<Customer> customerById = customerDao.findById(customerId);
		if (!customerById.isPresent()) {
			logger.info("No Customer with customer id {} present for updation", customerId);
			return null;
		}
		deleteBankAccountById(customerId, bankAccount.getBankId());
		return getUpdatedBankDetails(bankAccount, customerById.get());
	}

	@Override
	public void deleteBankAccountById(Long customerId, Long bankId) {

		List<BankAccount> bankAccountListById = bankAccountDao.findByBankId(bankId);
		Optional<BankAccount> deleteBank = bankAccountListById.stream()
				.filter(account -> account.getCustomer().getCustomerId() == customerId).findAny();
		if(!deleteBank.isPresent()) {
			logger.info("No Bank Account with bank id {} present for deletion", bankId);
			return;
		}
		bankAccountDao.delete(deleteBank.get());
	}

	public BankAccount getUpdatedBankDetails(BankAccount bankDetails, Customer customer) {
		BankAccount newBankAccount = new BankAccount();
		newBankAccount.setAccountNumber(bankDetails.getAccountNumber());
		newBankAccount.setAmount(bankDetails.getAmount());
		newBankAccount.setBankId(bankDetails.getBankId());
		newBankAccount.setBankName(bankDetails.getBankName());
		if (customer != null) {
			newBankAccount.setCustomer(customer);
		}
		return bankAccountDao.save(newBankAccount);
	}

	@Override
	public long getAmount(Long customerId, Long bankId) {
		Optional<Customer> customerById = customerDao.findById(customerId);
		if (!customerById.isPresent()) {
			return 0l;
		}
		List<BankAccount> customerBankList = customerById.get().getBankAccount();
		Optional<BankAccount> bankAmount = customerBankList.stream().filter(account -> account.getBankId() == bankId).findAny();
		if(!bankAmount.isPresent()) {
			logger.info("No bank account with id {} is present to fetch the amount", bankId);
			return 0l;
		}
		return bankAmount.get().getAmount();
	}
}
