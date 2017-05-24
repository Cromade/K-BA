package kba.view.layout.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kba.MainApp;
import kba.model.Basket;

import java.util.List;

public class BasketManagementLayoutController {

	@FXML
	private TextField researchField;
    @FXML
    private TableView<Basket> basketTable;
    @FXML
    private TableColumn<Basket, String> nameColumn;
    @FXML
    private TableColumn<Basket, String> GroupNameColumn;
    @FXML
    private TableColumn<Basket, String> StatusColumn;
    @FXML
    private TableColumn<Basket, Double> totalColumn;
    @FXML
    private Button detailButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button validateButton;

	private MainApp mainApp;
    private ObservableList<Basket> basketData = FXCollections.observableArrayList();
	
	public BasketManagementLayoutController() {
	}

    @FXML
    private void initialize() {

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        GroupNameColumn.setCellValueFactory(cellData -> cellData.getValue().groupNameProperty());
        StatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        totalColumn.setMinWidth(50);
        totalColumn.setMaxWidth(50);

        // listener
        basketTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedProduct) -> setButton(selectedProduct));
    }

    public void setDataInTable() {
        //get the preference of the user in db
        //TODO

        //to delete when db up
        basketData = mainApp.getBasketData();
        basketTable.setItems(basketData);
    }

    private void setButton(Basket selectedBasket) {
        detailButton.setOnAction(lambda->{
            if (selectedBasket != null) {
                mainApp.showBasketDetailDialog(selectedBasket);
            }
        });
        quitButton.setOnAction(lambda->{
            if (selectedBasket != null) {
                removeFromTable(selectedBasket);
            }
        });
        validateButton.setOnAction(lambda -> {
            if (selectedBasket != null) {
                if (selectedBasket.getStatus().equals("Validé !")) {
                    selectedBasket.setInvalidated();
                } else {
                    selectedBasket.setValidated();
                }
            }
        });
    }

    public void removeFromTable(Basket selectedBasket) {

        int selectedIndex = basketTable.getSelectionModel().getSelectedIndex();
        basketTable.getItems().remove(selectedIndex);

        //recuperation of the preference in db
        //TODO

        //to delete when db up
        basketData = mainApp.getBasketData();

        if(basketData != null) {
            basketData.remove(selectedBasket);
        }

        //update in db
        //TODO

        //To remove
        mainApp.setBasketData(basketData);
    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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
		dialogPane.getStylesheets().add(getClass().getResource("../../style/MainTheme.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");

		alert.showAndWait();
	}

	@FXML
    private void handleNewBasket() {
	    //dialog creation
    }
}
