package com.pj.renttracker.dao;

import java.util.List;

import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.search.ContractSearchCriteria;

public interface ContractDao {

	List<Contract> getAll();
	
	void save(Contract contract);
	
	Contract get(long id);

	List<Contract> search(ContractSearchCriteria criteria);
	
}
