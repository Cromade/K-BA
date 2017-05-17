package kba.view.layout.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import kba.MainApp;
import kba.model.Preference;
import kba.model.Product;

public class PreferenceManagementLayoutController {

	@FXML
    private TextField researchField;
	@FXML
    private TableView<Product> productTable;
    @FXML
	private TableColumn<Product, ImageView> imageColumn = new TableColumn<Product, ImageView>("Images");
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;

	private MainApp mainApp;
	
	@FXML
	private void initialize() {

		imageColumn.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("productImg"));
		imageColumn.setMinWidth(70);
		imageColumn.setMaxWidth(70);
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
		priceColumn.setMinWidth(50);
		priceColumn.setMaxWidth(50);
		
		// listener
		productTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedProduct) -> showProductDetails(selectedProduct));
	}
	
	public PreferenceManagementLayoutController() {
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	private void showProductDetails(Product selectedProduct) {
		if (selectedProduct != null) {
			mainApp.showProductDetailDialog(selectedProduct, true);
		}
	}
	
	public void setDataInTable() {
		//get the preference of the user in db
		Preference preferences = new Preference();
		//TODO
		
		//to delete when db up
		preferences = mainApp.getPreference();
		
		// Add observable list data to the table
        productTable.setItems(preferences.getPreferenceList());
	}
	
	@FXML
	private void handleSearch() {
		String toSearch = researchField.getText();
		
		if(toSearch != null) {
			if(toSearch.length() > 0) {
				//research in db
				//TODO
				return;
			}
		} 
		
		// Show the error message.
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Erreur");
        alert.setHeaderText("Le champ de recherche est vide");
        
        alert.showAndWait();
	}
}
