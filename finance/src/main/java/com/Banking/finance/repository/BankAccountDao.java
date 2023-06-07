package com.Banking.finance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.Banking.finance.entity.BankAccount;
import java.util.List;

@Repository
public interface BankAccountDao extends CrudRepository<BankAccount, Long>{

	List<BankAccount> findByBankId(long bankId);
}
