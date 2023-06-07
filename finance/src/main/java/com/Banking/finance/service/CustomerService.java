package com.Banking.finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;

import com.Banking.finance.dto.TransferAmountDto;
import com.Banking.finance.entity.Customer;

public interface CustomerService {

	// Save operation
	Customer saveCustomer(Customer customer);

	// Read operation
	List<Customer> fetchCustomer();

	// Update operation
	boolean updateCustomer(Customer customer, Long customerId);

	// Delete operation
	void deleteCustomerById(Long customerId);

	// Find a specific customer using its id
	Optional<Customer> findCustomerById(Customer customer);

	// Operation to transfer amount from one bank to another
	Customer transferAmount(@RequestBody TransferAmountDto transferAmountDto);

	//Maintains Bank Account transaction history
	void transactionHistory(long customerId, String customerName, long bankId, String bankName,
			long amounTransfered, String operation);
}
