package com.pj.renttracker.dao;

import java.util.List;

import com.pj.renttracker.model.Tenant;

public interface TenantDao {

	List<Tenant> getAll();
	
	void save(Tenant tenant);
	
	Tenant get(long id);
	
}
