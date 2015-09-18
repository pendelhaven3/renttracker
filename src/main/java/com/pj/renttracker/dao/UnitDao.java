package com.pj.renttracker.dao;

import java.util.List;

import com.pj.renttracker.model.Unit;

public interface UnitDao {

	List<Unit> getAll();
	
	void save(Unit unit);
	
	Unit get(long id);
	
}
