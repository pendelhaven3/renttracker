package com.pj.renttracker.controller;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

	@Autowired protected StageController stageController;
	
	public abstract void updateDisplay();
	
}
