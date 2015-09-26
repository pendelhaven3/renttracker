package com.pj.renttracker.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.pj.renttracker.dao.ContractRentDao;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractRent;

@Repository
public class ContractRentDaoImpl implements ContractRentDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<ContractRent> findAllByContract(Contract contract) {
		TypedQuery<ContractRent> query = 
				entityManager.createQuery("select r from ContractRent r where r.parent = ? order by r.rentDate desc", 
						ContractRent.class);
		query.setParameter(1, contract);
		return query.getResultList();
	}

	@Override
	public void save(ContractRent rent) {
		if (rent.getId() == null) {
			entityManager.persist(rent);
		} else {
			entityManager.merge(rent);
		}
	}

	@Override
	public ContractRent get(long id) {
		return entityManager.find(ContractRent.class, id);
	}

}
