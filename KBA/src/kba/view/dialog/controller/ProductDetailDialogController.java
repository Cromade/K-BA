package kba.view.dialog.controller;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kba.model.Basket;
import kba.model.Preference;
import kba.model.Product;
import kba.MainApp;

public class ProductDetailDialogController {
	
	@FXML
	private ImageView productImgView;
	@FXML
	private Label nameProductLabel;
	@FXML
	private Label manufacturerLabel;
	@FXML
	private Label priceLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private ChoiceBox<String> addToChoiceBox;
	@FXML
	private ChoiceBox<Integer> numberChoiceBox;
	@FXML
	private Button preferenceButton;

	private Stage dialogStage;
	private Product selectedProduct;
	private ObservableList<Basket> baskets;
	private MainApp mainApp;
	
	@FXML
    private void initialize() {
		numberChoiceBox.getItems().add(1);
		numberChoiceBox.getItems().add(2);
		numberChoiceBox.getItems().add(3);
		numberChoiceBox.getItems().add(4);
		numberChoiceBox.getItems().add(5);
		numberChoiceBox.getItems().add(6);
		numberChoiceBox.getItems().add(7);
		numberChoiceBox.getItems().add(8);
		numberChoiceBox.getItems().add(9);
		numberChoiceBox.getItems().add(10);
		numberChoiceBox.setValue(1);
	}
	
	public ProductDetailDialogController() {
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	//to delete
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void setChoiceBoxes() {
		//recuperation of the categry in db
		//TODO
						
		//to delete when db up
		baskets = mainApp.getBasketData();
		
		boolean first = true;				
		if (!baskets.isEmpty()) {
			for (Basket basket : baskets) {
				if (first) {
					addToChoiceBox.setValue(basket.getName());
					first = false;
				}
				addToChoiceBox.getItems().add(basket.getName());
			}
		}
	}
	
	public void setProduct(Product product) {
		if (product != null) {
	        this.selectedProduct = product;
	
	        productImgView.setImage(product.getProductImg().getImage());
	        nameProductLabel.setText(selectedProduct.getName());
	        manufacturerLabel.setText(selectedProduct.getManufacturer());
	        priceLabel.setText(selectedProduct.getPrice().toString());
	        descriptionLabel.setText(selectedProduct.getDescription());
		} else {
			Image image = null;
			BufferedImage img = null;
			try {
			    img = ImageIO.read(new FileInputStream("resources/default.png"));
			    image = SwingFXUtils.toFXImage(img, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			productImgView.setImage(image);
	        nameProductLabel.setText("Name");
	        manufacturerLabel.setText("Manufacturer");
	        priceLabel.setText("0.0");
	        descriptionLabel.setText("Description");
		}
    }
	
	public void setButtonSpecific() {
		preferenceButton.setText("Retirer des preferences");
		preferenceButton.setOnAction(lambda->{
			handleRemoveFromPreference();
		});
	}
	
	@FXML
	private void handleRemoveFromPreference() {
		//recuperation of the preference in db
		//TODO
						
		//to delete when db up
		Preference pref = mainApp.getPreference();
		
		if(pref != null) {
			pref.getPreferenceList().remove(selectedProduct);
		}
		
		mainApp.setPreference(pref);
		
		dialogStage.close();
	}
		
	@FXML
	private void handleAddTo() {
		if (baskets.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Attention");
            alert.setHeaderText("Vous n'avez pas de panier allez en créer un !");

            alert.showAndWait();
		} else {
			if (addToChoiceBox.getValue() == null || numberChoiceBox.getValue() == null) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Erreur");
	            alert.setHeaderText("Selectionnez un panier !");
	
	            alert.showAndWait();
			} else {
				String basketChoosen = (String) addToChoiceBox.getValue();
				int nbProduct = numberChoiceBox.getValue().intValue();
				
				for (Basket basket : baskets) {
					if (basket.getName().equals(basketChoosen)) {
						basket.addProduct(selectedProduct, nbProduct);
					}
				}
				
				dialogStage.close();
			}
		}
	}
	
	@FXML
	private void handleAddToPreference() {
		//recuperation of the preference in db
		//TODO
						
		//to delete when db up
		Preference pref = mainApp.getPreference();
		
		if(pref != null) {
			boolean result = pref.addPreferenceList(selectedProduct);
			if(result) {
				// Show the information message.
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Information");
	            alert.setHeaderText("Produit deja present dans la liste");

	            alert.showAndWait();
			} else {
				//save in db
				//TODO
				
				mainApp.setPreference(pref);
				
				Alert alert = new Alert(AlertType.INFORMATION);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Information");
	            alert.setHeaderText("Le Produit a ete ajouté au preferences");

	            alert.showAndWait();
			}
		} else {
			// Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur");

            alert.showAndWait();
		}
	}
	
	@FXML
	private void handleReturn() {
		dialogStage.close();
	}
}
