package com.pj.renttracker.dao;

import com.pj.renttracker.model.ContractPayment;

public interface ContractPaymentDao {

	void save(ContractPayment payment);
	
	ContractPayment get(long id);

	void delete(ContractPayment payment);
	
}
