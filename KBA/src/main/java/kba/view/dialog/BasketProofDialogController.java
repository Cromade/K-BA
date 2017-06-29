package kba.view.dialog;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kba.model.Basket;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class BasketProofDialogController {

    @FXML
    private ImageView basketProofImgView;
    @FXML
    private Label infoLabel;

    private Basket selectedBasket;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void showProof(Basket selectedBasket) {
        this.selectedBasket = selectedBasket;
        if (selectedBasket.getProofImage() != null) {
            infoLabel.setText("");
            basketProofImgView.setImage(selectedBasket.getProofImage());
        }
    }

    @FXML
    private void handleNewProof() {
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

                selectedBasket.setProofImg(image);
                basketProofImgView.setImage(image);
                infoLabel.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReturn() {
        dialogStage.close();
    }
}
