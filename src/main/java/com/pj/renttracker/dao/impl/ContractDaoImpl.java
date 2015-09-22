package com.pj.renttracker.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.pj.renttracker.dao.ContractDao;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.search.ContractSearchCriteria;

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

	@Override
	public List<Contract> search(ContractSearchCriteria criteria) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Contract> queryCriteria = builder.createQuery(Contract.class);
		Root<Contract> contract = queryCriteria.from(Contract.class);
		
		List<Predicate> predicates = new ArrayList<>();
		if (!criteria.getDueDates().isEmpty()) {
			predicates.add(contract.get("dueDate").in(criteria.getDueDates()));
		}
		queryCriteria.where(predicates.toArray(new Predicate[]{}));
		
		return entityManager.createQuery(queryCriteria).getResultList();
	}

}
