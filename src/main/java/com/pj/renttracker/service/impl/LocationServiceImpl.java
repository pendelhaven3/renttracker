package com.pj.renttracker.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.renttracker.dao.LocationDao;
import com.pj.renttracker.model.Location;
import com.pj.renttracker.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired private LocationDao locationDao;
	
	@Override
	public List<Location> getAllLocations() {
		return locationDao.getAll();
	}

	@Transactional
	@Override
	public void save(Location Location) {
		locationDao.save(Location);
	}

	@Override
	public Location getLocation(long id) {
		return locationDao.get(id);
	}

}
