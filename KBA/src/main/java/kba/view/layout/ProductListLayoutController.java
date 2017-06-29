package kba.view.layout;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import kba.MainApp;
import kba.model.Category;
import kba.model.Preference;
import kba.model.Product;

public class ProductListLayoutController {
	@FXML
    private TextField researchField;
	@FXML
	private ComboBox<String> categoriesComboBox;
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

	private MainApp mainApp;
	List<Category> categories = new ArrayList<Category>();
	
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
		
		productTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedProduct) -> detailButton.setOnAction(lambda-> showProductDetails(selectedProduct)));
	}
	
	private void showProductDetails(Product selectedProduct) {
		if (selectedProduct != null) {
			Preference pref = mainApp.getPreference();
			if (pref.getPreferenceList().contains(selectedProduct)) {
				mainApp.showProductDetailDialog(selectedProduct, true);
			} else {
				mainApp.showProductDetailDialog(selectedProduct, false);
			}
		}
	}

	public void setCategoriesInMenuItem() {
		//recuperation of the categry in db
		//TODO
				
		//to delete when db up
		categories = mainApp.getCategoriesData();

		//default category
		categoriesComboBox.getItems().add("Tous");
		categoriesComboBox.setValue("Tous");
		categoriesComboBox.setVisibleRowCount(5);

		if (!categories.isEmpty()) {
			for (Category category : categories) {
				categoriesComboBox.getItems().add(category.getName());
			}
		}

		setDataInTable();
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void setDataInTable() {
		//get the product list in DB (last 30 products)
		ObservableList<Product> products = FXCollections.observableArrayList();
		//TODO
		
		//to delete when db up
		products = mainApp.getProductData();
		int cmp = 0;
        ObservableList<Product> productSorted = FXCollections.observableArrayList();
		for (Product product : products) {
			productSorted.add(product);
			cmp++;
			if (cmp == 30) {
			    break;
            }
		}
        //---------------------

		
		// Add observable list data to the table
        // modify productSorted by product
        productTable.setItems(productSorted);
	}
	
	@FXML
	private void handleSearch() {
		String toSearch = researchField.getText();
		String category = categoriesComboBox.getValue();
		
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
