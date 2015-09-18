package com.pj.renttracker.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.renttracker.dao.ContractDao;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired private ContractDao contractDao;
	
	@Override
	public List<Contract> getAllContracts() {
		return contractDao.getAll();
	}

	@Transactional
	@Override
	public void save(Contract contract) {
		contractDao.save(contract);
	}

	@Override
	public Contract getContract(long id) {
		return contractDao.get(id);
	}

}
