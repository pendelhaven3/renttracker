package com.pj.renttracker;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pj.renttracker.controller.StageController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		String[] configLocations = new String[] {"applicationContext.xml", "datasource.xml"};
		
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocations);
		context.registerShutdownHook();
		
		Bootstrap bootstrap = context.getBean(Bootstrap.class);
		bootstrap.run();
		
		StageController stageController = context.getBean(StageController.class);
		stageController.setStage(stage);
		stageController.showMainMenuScreen();
		stage.show();
	}

}