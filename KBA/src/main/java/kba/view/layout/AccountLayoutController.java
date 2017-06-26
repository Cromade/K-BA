package kba.view.layout;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.FileChooser;
import kba.MainApp;
import kba.model.User;

public class AccountLayoutController {

	private MainApp mainApp;
	private User connectedUser;

	@FXML
	private Label lastnameLabel;
	@FXML
	private Label firstnameLabel;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label birthdayLabel;
	@FXML
	private Label addressLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private ImageView profileImgView;

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void setUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }
	
	public void showUserDetails() {
        if (connectedUser != null) {
            // Fill the labels with user info

            firstnameLabel.setText(connectedUser.getFirstname());
            lastnameLabel.setText(connectedUser.getLastname());
            usernameLabel.setText(connectedUser.getUsername());
            emailLabel.setText(connectedUser.getEmail());
            birthdayLabel.setText(connectedUser.getBirthday());
            addressLabel.setText(connectedUser.getAddress());
            cityLabel.setText(connectedUser.getCity());
            postalCodeLabel.setText(connectedUser.getPostalCode());
            profileImgView.setImage(connectedUser.getProfileImg());
            
        } else {
            firstnameLabel.setText("");
            lastnameLabel.setText("");
            usernameLabel.setText("");
            emailLabel.setText("");
            birthdayLabel.setText("");
            addressLabel.setText("");
            cityLabel.setText("");
            postalCodeLabel.setText("");
            Image image = null;
			BufferedImage img = null;
			try {
			    img = ImageIO.read(new FileInputStream("resources/user.png"));
			    image = SwingFXUtils.toFXImage(img, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
            profileImgView.setImage(image);
        }
    }
	
	@FXML
	private void handleManagePlugin() {
		mainApp.showPluginManagement();
	}

	@FXML
	private void handleChangeImage() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg")
		);
        try {
            File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
            if (file != null) {
                Image image = null;
                BufferedImage img = null;
                img = ImageIO.read(new FileInputStream(file));
                image = SwingFXUtils.toFXImage(img, null);

                connectedUser.setProfileImg(image);
                profileImgView.setImage(image);
                ImageView rootProfileImgView = (ImageView) mainApp.getRootLayout().getTop().lookup("#profileImgView");
                rootProfileImgView.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void handleEditUser() {
		if (connectedUser != null) {
	    	boolean okClicked = mainApp.showUserEditDialog(connectedUser, "Modification du compte");
	        if (okClicked) {
	        	// save changes
	        	// set the new info to connectedUser
	        	mainApp.setConnectedUser(connectedUser);
	        	//refresh the account layout
	        	showUserDetails();
	        }
	    } 
	}
	
	@FXML
	private void handleDeleteUser() {
		if (connectedUser != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Suppression de compte");
            alert.setHeaderText("Attention");
            alert.setContentText("Etes-vous sur de vouloir supprimer votre compte?");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
            dialogPane.getStyleClass().add("myDialog");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
        	    	// delete user in db
                	mainApp.initLoginLayout();
                }
            });
	    } 
	}
}
