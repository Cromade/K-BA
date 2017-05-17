package kba.view.menu.controller;

import javafx.fxml.FXML;
import kba.MainApp;

public class LeftMenuLayoutController {

	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void handleChangeToHome() {
		mainApp.showMainLayout();
	}

	@FXML
	private void handleChangeToBasketManagement() {
		mainApp.changeLayoutToBasketManagement();
	}
	
	@FXML
	private void handleChangeToPreferenceManagement() {
		mainApp.changeLayoutToPreferenceManagement();
	}
	
	@FXML
	private void handleChangeToProductList() {
		mainApp.changeLayoutToProductList();
	}
	
	@FXML
	private void handleChangeToAccount() {
		mainApp.changeLayoutToAccount();
	}
	
	@FXML
	private void handleChangeToGroupManagement() {
		mainApp.changeLayoutToGroupManagement();
	}
}
