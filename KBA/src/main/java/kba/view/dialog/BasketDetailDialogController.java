package kba.view.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kba.MainApp;
import kba.model.*;


public class BasketDetailDialogController {

    @FXML
    private TextField researchField;
    @FXML
    private TableView<BasketProduct> productTable;
    @FXML
    private TableColumn<BasketProduct, String> nameColumn;
    @FXML
    private TableColumn<BasketProduct, String> descriptionColumn;
    @FXML
    private TableColumn<BasketProduct, Double> priceColumn;
    @FXML
    private TableColumn<BasketProduct, Long> quantityColumn;
    @FXML
    private TableColumn<BasketProduct, Double> totalColumn;
    @FXML
    private Button detailButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button favouriteButton;

    private MainApp mainApp;
    private Stage dialogStage;
    private Basket referencedBasket;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().nameProperty());
        nameColumn.setMinWidth(150);
        nameColumn.setMaxWidth(150);
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().descriptionProperty());
        descriptionColumn.setMinWidth(268);
        descriptionColumn.setMaxWidth(268);
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().priceProperty().asObject());
        priceColumn.setMinWidth(50);
        priceColumn.setMaxWidth(50);
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        quantityColumn.setMinWidth(50);
        quantityColumn.setMaxWidth(50);
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        totalColumn.setMinWidth(50);
        totalColumn.setMaxWidth(50);

        // listener
        productTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedProduct) -> setButton(selectedProduct));
    }

    private void setButton(BasketProduct selectedProduct) {
        detailButton.setOnAction(lambda->{
            if (selectedProduct != null) {
                mainApp.showBasketProductDetailDialog(selectedProduct, referencedBasket);
            }
        });
        removeButton.setOnAction(lambda->{
            if (selectedProduct != null) {
                removeFromTable(selectedProduct);
                referencedBasket.removeProduct(selectedProduct, selectedProduct.getQuantity().intValue());
            }
        });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setDataInTableByBasket(Basket basket) {
        this.referencedBasket = basket;
        productTable.setItems(basket.getProductList());
    }

    @FXML
    private void handleSearch() {
        String toSearch = researchField.getText();

        if(toSearch != null) {
            if(toSearch.length() > 0) {
                //research in db
                //TODO
                return;
            }
        }

        // Show the error message.
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Erreur");
        alert.setHeaderText("Le champ de recherche est vide");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
        dialogPane.getStyleClass().add("myDialog");

        alert.showAndWait();
    }

    public void setFavouriteDisable() {
        favouriteButton.setDisable(true);
        favouriteButton.setText("Deja favori !");
    }

    @FXML
    private void handleFavourite() {
        //Get the favourite basket in DB
        //TODO

        //to delete
        ObservableList<Basket> basketData = mainApp.getBasketData();

        //update the old favourite basket
        for (Basket basket : basketData) {
            if (basket.getIsFavourite()) {
                basket.setFavourite(false);
                break;
            }
        }

        referencedBasket.setFavourite(true);
        setFavouriteDisable();
    }

    public void removeFromTable(BasketProduct productToDelete) {

        int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
        productTable.getItems().remove(selectedIndex);

        //recuperation of the preference in db
        //TODO

        //to delete when db up
        Preference preference = mainApp.getPreference();

        if(preference != null) {
            preference.getPreferenceList().remove(productToDelete);
        }

        //update in db
        //TODO

        //To remove
        mainApp.setPreference(preference);
    }

    @FXML
    private void handleReturn() {
        dialogStage.close();
    }
}
