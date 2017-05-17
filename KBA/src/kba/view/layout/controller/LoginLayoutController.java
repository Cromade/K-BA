package kba.view.layout.controller;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kba.MainApp;
import kba.model.User;
import kba.util.InputFieldTests;

public class LoginLayoutController {
	@FXML
	private ImageView logoImgView;
	@FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
	
	private MainApp mainApp;

	@FXML
	private void initialize() {
		Image image = null;
		BufferedImage img = null;
		try {
			img = ImageIO.read(new FileInputStream("resources/logo.png"));
			image = SwingFXUtils.toFXImage(img, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logoImgView.setImage(image);
	}

	public LoginLayoutController() {
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
	private void handleConnect() {
		//input tests
		String errorMessage = InputFieldTests.isEmailAndPasswordValid(emailField.getText(), passwordField.getText());
		if (errorMessage.length() > 0) {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Erreur");
            alert.setHeaderText("Certains champs sont invalides :");
            alert.setContentText(errorMessage);

            alert.showAndWait();
		} else {
			//compare with db
			//get and set the user data
			User connectedUser = new User();
			//TODO
			
			//some default data to delete when db connection will be made
			connectedUser.setLastname("Jean");
			connectedUser.setFirstname("Person");
			connectedUser.setUsername("Mr nobody");
			connectedUser.setEmail(emailField.getText());
			connectedUser.setPassword(passwordField.getText());
			connectedUser.setBirthday("06/09/1994");
			connectedUser.setAddress("5 rue de truc");
			connectedUser.setCity("cityville");
			connectedUser.setPostalCode("75000");
			Image image = null;
			BufferedImage img = null;
			try {
			    img = ImageIO.read(new FileInputStream("resources/usertmp.jpg"));
			    image = SwingFXUtils.toFXImage(img, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			connectedUser.setProfileImg(image);
			
			//then change the view
			mainApp.setConnectedUser(connectedUser);
			mainApp.initRootLayout();
			mainApp.showMainLayout();
		}
	}
	
	@FXML
    private void handleNewUser() {
        User tempUser = new User();
        boolean okClicked = mainApp.showUserEditDialog(tempUser, "Inscription");
        if (okClicked) {
        	Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Validation");
            alert.setHeaderText("Votre compte a bien été créé !");

            alert.showAndWait();
        }
    }
}
