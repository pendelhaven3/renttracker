package com.pj.renttracker.dao;

import java.util.List;

import com.pj.renttracker.model.Contract;

public interface ContractDao {

	List<Contract> getAll();
	
	void save(Contract contract);
	
	Contract get(long id);
	
}
