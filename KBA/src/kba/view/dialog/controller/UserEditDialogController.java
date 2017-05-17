package kba.view.dialog.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import kba.model.User;
import kba.util.InputFieldTests;

public class UserEditDialogController {
    @FXML
    private TextField lastnameField;
	@FXML
    private TextField firstnameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalCodeField;

    private Stage dialogStage;
    private User connectedUser;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(User user) {
        this.connectedUser = user;

        lastnameField.setText(user.getLastname());
        firstnameField.setText(user.getFirstname());
        usernameField.setText(user.getUsername());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        passwordField.setPromptText("Min 5 caracteres");
        birthdayField.setText(user.getBirthday());
        birthdayField.setPromptText("dd/mm/yyyy");
        addressField.setText(user.getAddress());
        cityField.setText(user.getCity());
        postalCodeField.setText(user.getPostalCode());
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
    	String errorMessage = InputFieldTests.isInputValid(lastnameField.getText(), firstnameField.getText(), usernameField.getText(), emailField.getText(), passwordField.getText(), birthdayField.getText(), addressField.getText(), cityField.getText(), postalCodeField.getText());
    	
    	if (errorMessage.length() > 0) {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Erreur");
            alert.setHeaderText("Certains champs sont invalides :");
            alert.setContentText(errorMessage);

            alert.showAndWait();
        } else {
        	connectedUser.setLastname(lastnameField.getText());
        	connectedUser.setFirstname(firstnameField.getText());
        	connectedUser.setUsername(usernameField.getText());
        	connectedUser.setEmail(emailField.getText());
        	connectedUser.setPassword(connectedUser.getPassword());
        	connectedUser.setBirthday(birthdayField.getText());
            connectedUser.setAddress(addressField.getText());
            connectedUser.setCity(cityField.getText());
            connectedUser.setPostalCode(postalCodeField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
