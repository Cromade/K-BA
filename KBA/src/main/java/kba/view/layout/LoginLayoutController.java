package kba.view.layout;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.net.URLConnection;
import java.util.*;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import kba.MainApp;
import kba.model.User;
import kba.network.NetworkResponse;
import kba.network.WebService;
import kba.util.InputFieldTests;
import org.json.JSONObject;

public class LoginLayoutController {
	@FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
	private ImageView logoImgView;
	
	private MainApp mainApp;

    @FXML
    private void initialize() {
        Image image = null;
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/images/logo.png"));
            image = SwingFXUtils.toFXImage(img, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logoImgView.setImage(image);

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleConnect();
            }
        });

        //TO DELETE
        emailField.setText("rick.sanchez@gmail.com");
        passwordField.setText("morty");
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
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
            dialogPane.getStyleClass().add("myDialog");
            
            alert.showAndWait();
		} else {
			//connect
            Map<String, String> params = new HashMap<>();
            params.put("email", emailField.getText());
            params.put("password", passwordField.getText());
            try {
                NetworkResponse networkConnexionResponse = WebService.post("http://51.255.196.182:3000/auth/login", params);

                Map<String, List<String>> header = networkConnexionResponse.getHeaders();
                JSONObject connexionResponse = new JSONObject(networkConnexionResponse.getBody());
                Map<String, String> requestHeader = new HashMap<>();
                String token = networkConnexionResponse.getHeaders().get("Token").toString();
                token = token.substring(1,token.length() - 1);

                requestHeader.put("Authorization", "Bearer "+ token);
                NetworkResponse networkGetUserResponse = WebService.get("http://51.255.196.182:3000/user/"+connexionResponse.get("uid"), requestHeader);
                JSONObject getUserResponse = new JSONObject(networkGetUserResponse.getBody());
                JSONObject userAddress = new JSONObject(getUserResponse.get("address").toString());

                User connectedUser = new User(getUserResponse.get("uid").toString(), getUserResponse.get("lastname").toString(), getUserResponse.get("firstname").toString(), getUserResponse.get("pseudo").toString(), getUserResponse.get("email").toString(),
                        getUserResponse.get("birthdate").toString(), userAddress.get("address").toString(), userAddress.get("city").toString(), userAddress.get("zipcode").toString());

                Image image = null;
                BufferedImage img = null;
                img = ImageIO.read(new FileInputStream("resources/usertmp.jpg"));
                image = SwingFXUtils.toFXImage(img, null);
                connectedUser.setProfileImg(image);

                //then change the view
                mainApp.setConnectedUser(connectedUser);
                mainApp.setRequestHeader(requestHeader);
                mainApp.initRootLayout();
                mainApp.showMainLayout();
            } catch (Exception e) {
                if (e.getLocalizedMessage().contains("401 for URL")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Attention !");
                    alert.setContentText("Certains champs ne correspondent pas...");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
                    dialogPane.getStyleClass().add("myDialog");
                    alert.showAndWait();
                }
            }

		}
	}
	
	@FXML
    private void handleNewUser() {
        User tempUser = new User();
        boolean okClicked = mainApp.showUserEditDialog(tempUser, "Inscription");
        if (okClicked) {

            Map<String, String> params = new HashMap<>();
            params.put("firstname", tempUser.getFirstname().toString());
            params.put("lastname", tempUser.getLastname().toString());
            params.put("pseudo", tempUser.getUsername().toString());
            params.put("email", tempUser.getEmail().toString());
            params.put("password", tempUser.getPassword().toString());
            params.put("birthdate", tempUser.getBirthday().toString());
            params.put("address", tempUser.getAddress().toString());
            params.put("zipcode", tempUser.getPostalCode().toString());
            params.put("city", tempUser.getCity().toString());

            try {
                NetworkResponse networkConnexionResponse = WebService.post("http://51.255.196.182:3000/auth/subscribe", params);
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Validation");
                alert.setHeaderText("Votre compte a bien été créé !");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
                dialogPane.getStyleClass().add("myDialog");

                alert.showAndWait();
            }catch (Exception e) {

                if (e.getLocalizedMessage().contains("401 for URL")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur lors de la sauvegarde");
                    alert.setContentText("Veuillez réessayer...");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
                    dialogPane.getStyleClass().add("myDialog");
                    alert.showAndWait();
                }
            }
        }
    }
}
