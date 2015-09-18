package com.pj.renttracker.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.renttracker.dao.TenantDao;
import com.pj.renttracker.model.Tenant;
import com.pj.renttracker.service.TenantService;

@Service
public class TenantServiceImpl implements TenantService {

	@Autowired private TenantDao tenantDao;
	
	@Override
	public List<Tenant> getAllTenants() {
		return tenantDao.getAll();
	}

	@Transactional
	@Override
	public void save(Tenant tenant) {
		tenantDao.save(tenant);
	}

	@Override
	public Tenant getTenant(long id) {
		return tenantDao.get(id);
	}

}
