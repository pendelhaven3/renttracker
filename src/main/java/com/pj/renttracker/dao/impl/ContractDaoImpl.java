package com.pj.renttracker.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.pj.renttracker.dao.ContractDao;
import com.pj.renttracker.model.Contract;

@Repository
public class ContractDaoImpl implements ContractDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Contract> getAll() {
		return entityManager.createQuery("select c from Contract c order by c.startDate desc", Contract.class)
				.getResultList();
	}

	@Override
	public void save(Contract contract) {
		if (contract.getId() == null) {
			entityManager.persist(contract);
		} else {
			entityManager.merge(contract);
		}
	}

	@Override
	public Contract get(long id) {
		return entityManager.find(Contract.class, id);
	}

}
