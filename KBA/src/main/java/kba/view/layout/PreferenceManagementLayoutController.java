package kba.view.layout;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
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
	@FXML
	private Button detailButton;
	@FXML
	private Button removeButton;

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
				(observable, oldValue, selectedProduct) -> setButton(selectedProduct));
	}
	
	private void setButton(Product selectedProduct) {
		detailButton.setOnAction(lambda->{
			if (selectedProduct != null) {
				mainApp.showProductDetailDialog(selectedProduct, true);
			}
		});
		removeButton.setOnAction(lambda->{
			if (selectedProduct != null) {
				removeFromTable(selectedProduct);
			}
		});
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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
	
	public void removeFromTable(Product productToDelete) {
		
        int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
		productTable.getItems().remove(selectedIndex);
		
		//recuperation of the preference in db
		//TODO
			
		//to delete when db up
		Preference preference = mainApp.getPreference();

		if(preference != null) {
			preference.getPreferenceList().remove(productToDelete);
		}
		
		//update in db
		//TODO
		
		//To remove
		mainApp.setPreference(preference);
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
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
        dialogPane.getStyleClass().add("myDialog");
        
        alert.showAndWait();
	}
}
