package com.pj.renttracker.service;

import java.util.List;

import com.pj.renttracker.model.Unit;

public interface UnitService {

	List<Unit> getAllUnits();
	
	void save(Unit unit);
	
	Unit getUnit(long id);
	
}
