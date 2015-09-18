package com.pj.renttracker.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.pj.renttracker.dao.ContractPaymentDao;
import com.pj.renttracker.model.ContractPayment;

@Repository
public class ContractPaymentDaoImpl implements ContractPaymentDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(ContractPayment payment) {
		if (payment.getId() == null) {
			entityManager.persist(payment);
		} else {
			entityManager.merge(payment);
		}
	}

	@Override
	public ContractPayment get(long id) {
		return entityManager.find(ContractPayment.class, id);
	}

	@Override
	public void delete(ContractPayment payment) {
		entityManager.remove(get(payment.getId()));
	}

}
