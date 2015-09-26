package com.pj.renttracker.dao;

import java.util.List;

import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractPayment;
import com.pj.renttracker.model.ContractRent;

public interface ContractPaymentDao {

	void save(ContractPayment payment);
	
	ContractPayment get(long id);

	void delete(ContractPayment payment);

	List<ContractPayment> findAllByContract(Contract contract);

	List<ContractPayment> findAllByRent(ContractRent rent);
	
}
