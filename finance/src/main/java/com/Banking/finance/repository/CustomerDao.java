package com.Banking.finance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.Banking.finance.entity.Customer;

@Repository
public interface CustomerDao extends CrudRepository<Customer, Long>{

}
