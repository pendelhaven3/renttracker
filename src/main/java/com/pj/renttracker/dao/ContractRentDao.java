package com.pj.renttracker.dao;

import java.util.List;

import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractRent;

public interface ContractRentDao {

	List<ContractRent> findAllByContract(Contract contract);
	
	void save(ContractRent rent);
	
	ContractRent get(long id);
	
}
