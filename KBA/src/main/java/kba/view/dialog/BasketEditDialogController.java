package kba.view.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kba.MainApp;
import kba.model.Basket;
import kba.model.Group;

public class BasketEditDialogController {

    @FXML
    private TextField basketName;
    @FXML
    private ChoiceBox<Group> groupChoice;
    @FXML
    private ChoiceBox<String> favouriteChoice;

    private MainApp mainApp;
    private Stage dialogStage;
    private Basket referencedBasket;
    private boolean isNew;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBasketData(Basket basket) {
        this.referencedBasket = basket;
        basketName.setText(basket.getName());
        groupChoice.setValue(basket.getGroup());
        if(basket.getIsFavourite()) {
            favouriteChoice.setValue("Oui");
        } else {
            favouriteChoice.setValue("Non");
        }
    }

    public void setChoiceBoxes() {
        //get the group list in DB
        //TODO
        ObservableList<Group> groupData = FXCollections.observableArrayList();

        //to delete
        groupData = mainApp.getGroupData();

        groupChoice.getItems().addAll(groupData);
        groupChoice.setValue(groupData.get(0));
        favouriteChoice.getItems().add("Non");
        favouriteChoice.getItems().add("Oui");
        favouriteChoice.setValue("Non");

    }

    @FXML
    private void handleOk() {
        if (basketName.getText().length() <= 0) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Erreur");
            alert.setHeaderText("Certains champs sont invalides :");
            alert.setContentText("Il faut donner un nom au groupe !");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
            dialogPane.getStyleClass().add("myDialog");

            alert.showAndWait();
        } else {
            referencedBasket.setName(basketName.getText());
            referencedBasket.setGroup(groupChoice.getValue());
            if (favouriteChoice.getValue().equals("Oui")) {
                //get the old favourite Basket
                //TODO
                //update the old favourite basket
                for (Basket basket : mainApp.getBasketData()) {
                    if (basket.getIsFavourite()) {
                        basket.setFavourite(false);
                        break;
                    }
                }

                referencedBasket.setFavourite(true);
            } else {
                referencedBasket.setFavourite(false);
            }

            //save the new basket
            // TODO
            if(isNew) {
                mainApp.getBasketData().add(referencedBasket);
            }

            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
}
