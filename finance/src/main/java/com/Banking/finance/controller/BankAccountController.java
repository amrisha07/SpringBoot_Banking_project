package com.Banking.finance.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Banking.finance.entity.BankAccount;
import com.Banking.finance.service.BankAccountService;

@RestController
@RequestMapping("/bankDetails")
public class BankAccountController {
	
	Logger logger = LoggerFactory.getLogger(BankAccountController.class);
	
	@Autowired
	private BankAccountService bankAccountService;

	@PostMapping("/addBank/{customerId}")
	public List<BankAccount> addCustomerBankAccount(@RequestBody List<BankAccount> bankAccount, @PathVariable("customerId") Long customerId) {
		logger.info("Adding bank account details for customer with id {}", customerId);
		return bankAccountService.saveBankDetails(bankAccount, customerId);
	}
	
	@GetMapping("/{customerId}")
	public List<BankAccount> fetchBankAccountList(@PathVariable("customerId") Long customerId) {
		logger.info("Get bank account for customer with id {}", customerId);
		return bankAccountService.fetchBankAccountList(customerId);
	}
	
	@PutMapping("/{customerId}")
	public BankAccount updateBankAccount(@RequestBody BankAccount bankAccount, @PathVariable("customerId") Long customerId) {
		logger.info("update bank account details for customer with id {} ", customerId);
		return bankAccountService.updateBankAccount(bankAccount, customerId);
	}
	
	@DeleteMapping("/{customerId}/{bankId}")
	public void deleteBankAccountById(@PathVariable("customerId") Long customerId, @PathVariable("bankId") Long bankId) {
		logger.info("Delete bank account with id: {}", bankId);
		bankAccountService.deleteBankAccountById(customerId, bankId);
	}
	
	@GetMapping("/{customerId}/{bankId}")
	public ResponseEntity<String> getAmount(@PathVariable("customerId") Long customerId, @PathVariable("bankId") Long bankId) {
		logger.info("Get balance for customer with id: {}", customerId);
		return ResponseEntity.status(HttpStatus.OK).body("Current Balance: " + bankAccountService.getAmount(customerId, bankId));
	}
}
