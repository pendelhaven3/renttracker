package com.pj.renttracker.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.renttracker.ControllerFactory;
import com.pj.renttracker.Parameter;
import com.pj.renttracker.model.Unit;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.Location;
import com.pj.renttracker.model.Tenant;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class StageController {

	private static final double WIDTH = 1200d;
	private static final double HEIGHT = 640d;
	
	@Autowired private ControllerFactory controllerFactory;
	
	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void loadSceneFromFXML(String file) {
		loadSceneFromFXML(file, null);
	}
	
	private void loadSceneFromFXML(String file, Map<String, Object> model) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(controllerFactory);
		
		Parent root = null;
		try {
			root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/" + file + ".fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		stage.setScene(null);
		stage.setScene(new Scene(root, WIDTH, HEIGHT));
		stage.getScene().getStylesheets().add("css/application.css");
		
		if (fxmlLoader.getController() instanceof AbstractController) {
			AbstractController controller = (AbstractController)fxmlLoader.getController();
			if (model != null && !model.isEmpty()) {
				mapParameters(controller, model);
			}
			controller.updateDisplay();
		}
	}

	private void mapParameters(AbstractController controller, Map<String, Object> model) {
		Class<? extends AbstractController> clazz = controller.getClass();
		for (String key : model.keySet()) {
			try {
				Field field = clazz.getDeclaredField(key);
				if (field != null && field.getAnnotation(Parameter.class) != null) {
					field.setAccessible(true);
					field.set(controller, model.get(key));
				}
			} catch (Exception e) {
				System.out.println("Error setting parameter " + key);
			}
		}
	}

	public void setTitle(String title) {
		stage.setTitle(title);
	}

	public void showMainMenuScreen() {
		loadSceneFromFXML("mainMenu");
	}

	public void showTenantListScreen() {
		loadSceneFromFXML("tenantList");
	}

	public void showAddTenantScreen() {
		loadSceneFromFXML("tenant");
	}

	public void showUpdateTenantScreen(Tenant tenant) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("tenant", tenant);
		
		loadSceneFromFXML("tenant", paramMap);
	}

	public void showUnitListScreen() {
		loadSceneFromFXML("unitList");
	}

	public void showAddUnitScreen() {
		loadSceneFromFXML("unit");
	}

	public void showUpdateUnitScreen(Unit unit) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("unit", unit);
		
		loadSceneFromFXML("unit", paramMap);
	}

	public void showContractListScreen() {
		loadSceneFromFXML("contractList");
	}

	public void showAddContractScreen() {
		loadSceneFromFXML("addEditContract");
	}

	public void showUpdateContractScreen(Contract contract) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("contract", contract);
		
		loadSceneFromFXML("addEditContract", paramMap);
	}

	public void showContractScreen(Contract contract) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("contract", contract);
		
		loadSceneFromFXML("contract", paramMap);
	}

	public void showUpcomingRentsScreen() {
		loadSceneFromFXML("upcomingRents");
	}

	public void showLocationListScreen() {
		loadSceneFromFXML("locationList");
	}

	public void showAddLocationScreen() {
		loadSceneFromFXML("location");
	}

	public void showUpdateLocationScreen(Location location) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("location", location);
		
		loadSceneFromFXML("location", paramMap);
	}

}