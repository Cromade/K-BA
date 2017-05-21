package kba.view.layout.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
	private MenuButton categoriesMenu;
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
	
	public ProductListLayoutController() {
	}
	
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
		Category tendance = null;
				
		if (!categories.isEmpty()) {
			for (Category category : categories) {
				MenuItem item = new MenuItem(category.getName());
				item.setOnAction(lambda->{ 
					setDataInTableByCategory(category);
					categoriesMenu.setText(category.getName());
				});
				categoriesMenu.getItems().add(item);
				if (category.getName().equals("Tendance")) {
					tendance = category;
				}
			}
		}
		
		if (tendance != null) {
			categoriesMenu.setText("Tendance");
			setDataInTableByCategory(tendance);
		}
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void setDataInTableByCategory(Category categorySelected) {
		//get the product list by category in db
		ObservableList<Product> products = FXCollections.observableArrayList();
		//TODO
		
		//to delete when db up
		products = mainApp.getProductData();
		ObservableList<Product> productSorted = FXCollections.observableArrayList();
		for (Product product : products) {
			if (product.getCategories() != null) {
				for (Category productCategory : product.getCategories()) {
					if (productCategory.equals(categorySelected)) {
						productSorted.add(product);
					}
				}
			}
		}
		
		// Add observable list data to the table
        productTable.setItems(productSorted);
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
        dialogPane.getStylesheets().add(getClass().getResource("../../style/MainTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        
        alert.showAndWait();
	}
	
}
