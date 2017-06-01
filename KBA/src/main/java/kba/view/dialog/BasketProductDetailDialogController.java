package kba.view.dialog;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kba.MainApp;
import kba.model.Basket;
import kba.model.BasketProduct;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class BasketProductDetailDialogController {

        @FXML
        private Label basketLabel;
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
        private ChoiceBox<Integer> numberChoiceBox;

        private Stage dialogStage;
        private BasketProduct selectedProduct;
        private Basket referencedBasket;
        private MainApp mainApp;

        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
        }
        public void setMainApp(MainApp mainApp) {
            this.mainApp = mainApp;
        }
        public void setReferencedBasket(Basket referencedBasket) {
            this.referencedBasket = referencedBasket;
            basketLabel.setText(referencedBasket.getName());
        }

        public void setNumberInChoiceBox() {
            numberChoiceBox.getItems().clear();
            for (int i = 1; i <= selectedProduct.getQuantity(); i++) {
                numberChoiceBox.getItems().add(i);
            }
            numberChoiceBox.setValue(1);
        }

        public void setProduct(BasketProduct product) {
            if (product != null) {
                this.selectedProduct = product;

                productImgView.setImage(selectedProduct.getProduct().getProductImg().getImage());
                nameProductLabel.setText(selectedProduct.getProduct().getName());
                manufacturerLabel.setText(selectedProduct.getProduct().getManufacturer());
                priceLabel.setText(selectedProduct.getProduct().getPrice().toString());
                descriptionLabel.setText(selectedProduct.getProduct().getDescription());
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

        @FXML
        private void handleRemove() {
            if (numberChoiceBox.getValue() != null) {
                referencedBasket.updateProduct(selectedProduct, numberChoiceBox.getValue());
                if (selectedProduct.getQuantity() == 0) {
                    referencedBasket.removeProduct(selectedProduct, numberChoiceBox.getValue());
                    dialogStage.close();
                }
                setNumberInChoiceBox();
            }
        }

        @FXML
        private void handleReturn() {
            dialogStage.close();
        }
}
