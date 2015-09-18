package com.pj.renttracker.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.renttracker.dao.UnitDao;
import com.pj.renttracker.model.Unit;
import com.pj.renttracker.service.UnitService;

@Service
public class UnitServiceImpl implements UnitService {

	@Autowired private UnitDao unitDao;
	
	@Override
	public List<Unit> getAllUnits() {
		return unitDao.getAll();
	}

	@Transactional
	@Override
	public void save(Unit unit) {
		unitDao.save(unit);
	}

	@Override
	public Unit getUnit(long id) {
		return unitDao.get(id);
	}

}
