package kba.view.layout.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kba.MainApp;
import kba.model.Basket;

public class MainLayoutController {

	@FXML
	private Label defaultBasketLabel;
	
	private MainApp mainApp;
	private ObservableList<Basket> baskets;
	
	public MainLayoutController() {
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void setDefaultBasketTotal() {
		//get in db
		//TODO
		
		//to delete
		baskets = mainApp.getBasketData();
		if (baskets.isEmpty()) {
			defaultBasketLabel.setText("Vide :(");
		} else {
			defaultBasketLabel.setText(baskets.get(0).getTotal() + "€");
		}
	}
	
	@FXML
	private void handleChangeToBasketManagement() {
		mainApp.changeLayoutToBasketManagement();
	}
	
	@FXML
	private void handleChangeToGroupManagement() {
		mainApp.changeLayoutToGroupManagement();
	}
}
