package com.Banking.finance.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.Banking.finance.dto.TransferAmountDto;
import com.Banking.finance.entity.Customer;
import com.Banking.finance.service.CustomerService;

@RestController
public class CustomerController {
	
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	private static final String PING_MESSAGE = "Server is up and running";
	
	@Autowired
	private CustomerService customerService;

	// Read operation
	@GetMapping("/ping")
	public String pingServer() {
		return PING_MESSAGE;
	}

	// Save operation
	@PostMapping("/customer")
	public ResponseEntity<List<Customer>> saveCustomer(@RequestBody List<Customer> customer) {
		logger.info("Save Customer request initiated");
		if(!customer.isEmpty()) {
			logger.info("Save Customer request completed");
			return ResponseEntity.status(HttpStatus.OK).body(customer.stream().filter(addCustomer -> {
				if(customerService.findCustomerById(addCustomer).isPresent()) {
					return false;
				}
				Optional<Customer> customerPresent = Optional.of(customerService.saveCustomer(addCustomer));
				return customerPresent.isPresent();
			}).toList());
		}
		logger.info("Save Customer could not be completed");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	// Read operation
	@GetMapping("/allCustomer")
	public ResponseEntity<List<Customer>> fetchCustomer() {
		logger.info("Request to fetch all customer details");
		return ResponseEntity.status(HttpStatus.OK).body(customerService.fetchCustomer());
	}

	// Update operation
	@PutMapping("/customer/{id}")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable("id") Long customerId) {
		logger.info("Update customer with customer Id: {}", customerId);
		if(customerService.updateCustomer(customer, customerId)) {
			logger.info("Customer successfully updated");
			return ResponseEntity.status(HttpStatus.OK).body("Customer Details updated");
		}
		logger.info("Customer was not updated");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	// Delete operation
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable("id") Long customerId) {
		logger.info("Delete customer with customer Id: {}", customerId);
		customerService.deleteCustomerById(customerId);
		return ResponseEntity.status(HttpStatus.OK).body("Customer Deleted successfully");
	}

	@PutMapping("/transferAmount")
	public ResponseEntity<String> transferAmount(@RequestBody TransferAmountDto transferAmountDto) {
		logger.info("Amount transfer operation initiated");
		if(customerService.transferAmount(transferAmountDto) == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body("Amount transfered successfully");
	}
}
