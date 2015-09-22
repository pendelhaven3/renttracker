package com.pj.renttracker.service;

import java.util.List;

import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractPayment;

public interface ContractService {

	List<Contract> getAllContracts();
	
	void save(Contract contract);
	
	Contract getContract(long id);

	void save(ContractPayment payment);

	void delete(ContractPayment payment);

	List<Contract> findContractsWithUpcomingDues();
	
}
