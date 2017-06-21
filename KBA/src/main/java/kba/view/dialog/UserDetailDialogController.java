package kba.view.dialog;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kba.model.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class UserDetailDialogController {

    @FXML
    private ImageView profileImgView;
    @FXML
    private Label lastnameLabel;
    @FXML
    private Label firstnameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(User selectedUser) {
        if (selectedUser != null) {

            profileImgView.setImage(selectedUser.getProfileImg());
            lastnameLabel.setText(selectedUser.getLastname());
            firstnameLabel.setText(selectedUser.getFirstname());
            usernameLabel.setText(selectedUser.getUsername());
            birthdayLabel.setText(selectedUser.getBirthday());
            emailLabel.setText(selectedUser.getEmail());
            addressLabel.setText(selectedUser.getAddress());
            postalCodeLabel.setText(selectedUser.getPostalCode());
            cityLabel.setText(selectedUser.getCity());
        } else {
            Image image = null;
            BufferedImage img;
            try {
                img = ImageIO.read(new FileInputStream("resources/default.png"));
                image = SwingFXUtils.toFXImage(img, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            profileImgView.setImage(image);
            lastnameLabel.setText("Lastname");
            firstnameLabel.setText("Firstname");
            usernameLabel.setText("Username");
            birthdayLabel.setText("Birthday");
            emailLabel.setText("Email");
            addressLabel.setText("Address");
            postalCodeLabel.setText("Postal Code");
            cityLabel.setText("City");
        }
    }

    @FXML
    private void handleReturn() {
        dialogStage.close();
    }
}
