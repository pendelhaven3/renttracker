package com.pj.renttracker.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.renttracker.dao.ContractDao;
import com.pj.renttracker.dao.ContractPaymentDao;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractPayment;
import com.pj.renttracker.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired private ContractDao contractDao;
	@Autowired private ContractPaymentDao contractPaymentDao;
	
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

	@Transactional
	@Override
	public void save(ContractPayment payment) {
		contractPaymentDao.save(payment);
	}

	@Transactional
	@Override
	public void delete(ContractPayment payment) {
		contractPaymentDao.delete(payment);
	}

}
