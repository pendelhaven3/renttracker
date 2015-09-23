package com.pj.renttracker.service;

import java.util.List;

import com.pj.renttracker.model.Location;

public interface LocationService {

	List<Location> getAllLocations();
	
	void save(Location location);
	
	Location getLocation(long id);
	
}
