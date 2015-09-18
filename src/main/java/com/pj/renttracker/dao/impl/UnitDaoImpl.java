package com.pj.renttracker.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.pj.renttracker.dao.UnitDao;
import com.pj.renttracker.model.Unit;

@Repository
public class UnitDaoImpl implements UnitDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Unit> getAll() {
		return entityManager.createQuery("select u from Unit u order by u.name", Unit.class).getResultList();
	}

	@Override
	public void save(Unit unit) {
		if (unit.getId() == null) {
			entityManager.persist(unit);
		} else {
			entityManager.merge(unit);
		}
	}

	@Override
	public Unit get(long id) {
		return entityManager.find(Unit.class, id);
	}

}
