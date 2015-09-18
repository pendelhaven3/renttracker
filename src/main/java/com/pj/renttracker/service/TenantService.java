package com.pj.renttracker.service;

import java.util.List;

import com.pj.renttracker.model.Tenant;

public interface TenantService {

	List<Tenant> getAllTenants();
	
	void save(Tenant tenant);
	
	Tenant getTenant(long id);
	
}
