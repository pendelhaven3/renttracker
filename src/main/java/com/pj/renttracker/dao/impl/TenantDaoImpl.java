package com.pj.renttracker.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.pj.renttracker.dao.TenantDao;
import com.pj.renttracker.model.Tenant;

@Repository
public class TenantDaoImpl implements TenantDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Tenant> getAll() {
		return entityManager.createQuery("select t from Tenant t order by t.name", Tenant.class).getResultList();
	}

	@Override
	public void save(Tenant tenant) {
		if (tenant.getId() == null) {
			entityManager.persist(tenant);
		} else {
			entityManager.merge(tenant);
		}
	}

	@Override
	public Tenant get(long id) {
		return entityManager.find(Tenant.class, id);
	}
	
}
