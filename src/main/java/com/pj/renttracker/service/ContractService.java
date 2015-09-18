package com.pj.renttracker.service;

import java.util.List;

import com.pj.renttracker.model.Contract;

public interface ContractService {

	List<Contract> getAllContracts();
	
	void save(Contract contract);
	
	Contract getContract(long id);
	
}
