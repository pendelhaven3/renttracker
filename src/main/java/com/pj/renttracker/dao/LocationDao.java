package com.pj.renttracker.dao;

import java.util.List;

import com.pj.renttracker.model.Location;

public interface LocationDao {

	List<Location> getAll();
	
	void save(Location location);
	
	Location get(long id);
	
}
