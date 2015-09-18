package com.pj.renttracker;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javafx.util.Callback;

@Component
public class ControllerFactory implements Callback<Class<?>, Object>, ApplicationContextAware {

	private ApplicationContext context;
	
	@Override
	public Object call(Class<?> clazz) {
		return context.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

}
