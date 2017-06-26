package kba.view.dialog;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kba.MainApp;
import kba.model.Group;
import kba.model.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class GroupEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private ImageView groupImgView;

    private Stage dialogStage;
    private Group selectedGroup;
    private User connectedUser;
    private MainApp mainApp;
    private boolean isNew = false;
    private boolean okClicked = false;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
    public boolean isOkClicked() {
        return okClicked;
    }

    public void setGroupDetails(Group group) {
        nameField.setText(group.getName());
        groupImgView.setImage(group.getImageGroup().getImage());
        selectedGroup = group;
        isNew = true;
    }

    @FXML
    private void handleOk() {
        if (nameField.getText() == null || nameField.getText().length() == 0) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Erreur");
            alert.setHeaderText("Certains champs sont invalides :");
            alert.setContentText("Le nom est invalide");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
            dialogPane.getStyleClass().add("myDialog");

            alert.showAndWait();
        } else {
            selectedGroup.setName(nameField.getText());
            if (isNew) {
                selectedGroup.addUserToGroup(connectedUser);
            }
            okClicked = true;

            dialogStage.close();
        }
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
            File file = fileChooser.showOpenDialog(dialogStage);
            if (file != null) {
                Image image = null;
                BufferedImage img = null;
                img = ImageIO.read(new FileInputStream(file));
                image = SwingFXUtils.toFXImage(img, null);

                selectedGroup.setImageGroup(image);
                groupImgView.setImage(image);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
