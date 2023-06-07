package com.Banking.finance.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Banking.finance.dto.TransferAmountDto;
import com.Banking.finance.entity.BankAccount;
import com.Banking.finance.entity.Customer;
import com.Banking.finance.repository.CustomerDao;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

	Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerDao customerDao;

	@Override
	public Customer saveCustomer(Customer customer) {
		Optional<Customer> customerPresent = customerDao.findById(customer.getCustomerId());
		if (customerPresent.isPresent()) {
			logger.info("customer is already present");
			return customer;
		}
		return customerDao.save(customer);
	}

	@Override
	public List<Customer> fetchCustomer() {
		return (List<Customer>) customerDao.findAll();
	}

	@Override
	public boolean updateCustomer(Customer customer, Long customerId) {
		Optional<Customer> customerbyId = customerDao.findById(customerId);
		if (!customerbyId.isPresent()) {
			logger.info("No Customer with customerId: {}  is present", customerId);
			return false;
		}
		logger.info("customer already exists. Updating");
		customerbyId.get().setCustomerId(customerId);
		customerbyId.get().setCustomerName(customer.getCustomerName());
		customerbyId.get().setBankAccount(customer.getBankAccount());
		customerDao.save(customerbyId.get());
		return true;
	}

	@Override
	public void deleteCustomerById(Long customerId) {
		Optional<Customer> customerbyId = customerDao.findById(customerId);
		if (!customerbyId.isPresent()) {
			logger.info("No Customer with customerId: {} is present for deletion.", customerId);
		}
		customerDao.deleteById(customerId);
		logger.info("Customer deleted successfully");
	}

	@Override
	public Optional<Customer> findCustomerById(Customer customer) {
		return customerDao.findById(customer.getCustomerId());
	}

	@Override
	public Customer transferAmount(TransferAmountDto transferAmountDto) {
		try {
		long amountTransfered = transferAmountDto.getAmount();
		
		Customer sourceCustomer = getAmountAfterTransaction(transferAmountDto.getSourceCustomerId(),
				transferAmountDto.getSourceBankId(), true, amountTransfered);
		Customer destinationCustomer = getAmountAfterTransaction(transferAmountDto.getDestinationCustomerId(),
				transferAmountDto.getDestinationBankId(), false, amountTransfered);
		
		customerDao.save(destinationCustomer);
		return customerDao.save(sourceCustomer);
		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public Customer getAmountAfterTransaction(long customerId, long bankId, boolean isSource, long amountTransfered) {
		Optional<Customer> customerById = customerDao.findById(customerId);
		if (!customerById.isPresent()) {
			logger.info("No customer with id: {} was found for amount transfer transaction.", customerId);
			return null;
		}
		List<BankAccount> customerBankAccount = customerById.get().getBankAccount();
		Optional<BankAccount> customerBank = customerBankAccount.stream()
				.filter(account -> account.getBankId() == bankId).findAny();
		if (!customerBank.isPresent()) {
			logger.info("No Bank Account with id: {} was found for transaction." , bankId);
			return null;
		}
		customerBankAccount.remove(customerBank.get());
		if (isSource) {
			if (customerBank.get().getAmount() >= amountTransfered) {
				customerBank.get().setAmount(customerBank.get().getAmount() - amountTransfered);
				transactionHistory(customerId, customerById.get().getCustomerName(), bankId,
						customerBank.get().getBankName(), amountTransfered, "Debit");
			}
		} else {
			customerBank.get().setAmount(customerBank.get().getAmount() + amountTransfered);
			transactionHistory(customerId, customerById.get().getCustomerName(), bankId,
					customerBank.get().getBankName(), amountTransfered, "Credit");
		}
		
		customerBankAccount.add(customerBank.get());
		customerById.get().setBankAccount(customerBankAccount);
		return customerById.get();
	}

	public void transactionHistory(long customerId, String customerName, long bankId, String bankName,
			long amounTransfered, String operation) {
		logger.info("Maintaing transaction history for customer {} for operation {}", customerName, operation);
		String fileName = "C:\\eclipse\\banking\\" + customerId + "_" + bankId + ".txt";
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss z");
		formatDate.setTimeZone(TimeZone.getTimeZone("IST"));
		String time = formatDate.format(new Date());
		String fileWrite = time + "\t" + customerId + "\t" + customerName + "\t" + bankId + "\t" + bankName + "\t"
				+ amounTransfered + "\t" + operation;
		File file = new File(fileName);
		try {
			if (file.createNewFile()) {
				fileWrite(fileName, fileWrite);
			} else {
				fileWrite(fileName, "\n" + fileWrite);
			}
		} catch (IOException e) {
			logger.info("Exception occured while trying to create transaction history file");
			e.printStackTrace();
		}
	}

	public void fileWrite(String fileName, String writeData) {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true))) {
			out.write(writeData);
			out.write("\n ****** \t ****** \t ****** \t ****** \t ****** \t ******");
		} catch (IOException e) {
			logger.info("Exception occured while trying to write on the transaction history file");
			e.printStackTrace();
		}
	}
}
