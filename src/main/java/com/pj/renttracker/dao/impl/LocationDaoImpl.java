package com.pj.renttracker.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.pj.renttracker.dao.LocationDao;
import com.pj.renttracker.model.Location;

@Repository
public class LocationDaoImpl implements LocationDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Location> getAll() {
		return entityManager.createQuery("select l from Location l order by l.name", Location.class).getResultList();
	}

	@Override
	public void save(Location location) {
		if (location.getId() == null) {
			entityManager.persist(location);
		} else {
			entityManager.merge(location);
		}
	}

	@Override
	public Location get(long id) {
		return entityManager.find(Location.class, id);
	}

}
