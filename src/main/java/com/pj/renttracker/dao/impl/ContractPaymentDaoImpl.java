package com.pj.renttracker.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.pj.renttracker.dao.ContractPaymentDao;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractPayment;
import com.pj.renttracker.model.ContractRent;

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

	@Override
	public List<ContractPayment> findAllByContract(Contract contract) {
		TypedQuery<ContractPayment> query = 
				entityManager.createQuery(
						"select r from ContractPayment r where r.parent = ? order by r.paymentDate desc", 
						ContractPayment.class);
		query.setParameter(1, contract);
		return query.getResultList();
	}

	@Override
	public List<ContractPayment> findAllByRent(ContractRent rent) {
		TypedQuery<ContractPayment> query = entityManager.createQuery(
						"select p from ContractPayment p where p.rent = :rent", 
						ContractPayment.class);
		query.setParameter("rent", rent);
		return query.getResultList();
	}

}
