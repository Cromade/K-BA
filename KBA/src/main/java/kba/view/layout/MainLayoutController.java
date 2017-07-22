package kba.view.layout;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import kba.MainApp;
import kba.model.*;
import kba.network.NetworkResponse;
import kba.network.WebService;
import kba.util.ReminderProduct;
import org.json.JSONArray;

import java.util.List;

public class MainLayoutController {

	@FXML
	private Label favouriteBasketTotalLabel;
	@FXML
	private Label favouriteBasketNameLabel;
	@FXML
    private Button favouriteBasketButton;
	@FXML
    private TableView<Group> groupTable;
    @FXML
    private TableColumn<Group, ImageView> imageGroupColumn = new TableColumn<>("Images");
    @FXML
    private TableColumn<Group, String> nameGroupColumn;
	@FXML
	private TableView<Product> reminderTable;
	@FXML
	private TableColumn<Product, ImageView> imageReminderColumn = new TableColumn<>("Images");
	@FXML
	private TableColumn<Product, String> nameReminderColumn;
	@FXML
    private ChoiceBox<Integer> numberChoiceBox;
	@FXML
    private Button addToButton;
	
	private MainApp mainApp;
	private User connectedUser;
	private ObservableList<Basket> baskets;
	private Basket favourite;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            numberChoiceBox.getItems().add(1);
            numberChoiceBox.getItems().add(2);
            numberChoiceBox.getItems().add(3);
            numberChoiceBox.getItems().add(4);
            numberChoiceBox.getItems().add(5);
            numberChoiceBox.getItems().add(6);
            numberChoiceBox.getItems().add(7);
            numberChoiceBox.getItems().add(8);
            numberChoiceBox.getItems().add(9);
            numberChoiceBox.getItems().add(10);
            numberChoiceBox.setValue(1);

            imageGroupColumn.setCellValueFactory(new PropertyValueFactory<>("imageGroup"));
            imageGroupColumn.setMinWidth(70);
            imageGroupColumn.setMaxWidth(70);
            nameGroupColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

            imageReminderColumn.setCellValueFactory(new PropertyValueFactory<>("productImg"));
            imageReminderColumn.setMinWidth(70);
            imageReminderColumn.setMaxWidth(70);
            nameReminderColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

            // listener
            reminderTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, selectedProduct) -> addToButton.setOnAction(lambda -> handleAddTo(selectedProduct)));
        });
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }
	
	public void setFavouriteBasketTotal() {
        favourite = new Basket();
        //get in db the favourite
        //TODO
        //to delete
        baskets = mainApp.getBasketData();

		for (Basket basket :baskets) {
			if (basket.getIsFavourite()) {
				favourite = basket;
			}
		}

		favouriteBasketNameLabel.setText(favourite.getName());
		if (favourite.getTotal() == 0) {
			favouriteBasketTotalLabel.setText("Vide :(");
		} else {
			favouriteBasketTotalLabel.setText(favourite.getTotal() + "€");
		}
        Basket finalFavourite = favourite;
        favouriteBasketButton.setOnAction(lambda-> {
            mainApp.showBasketDetailDialog(finalFavourite);
            setReminderTable();
            setFavouriteBasketTotal();
        });
	}

	public void setReminderTable() {
        reminderTable.setItems(ReminderProduct.getReminder(mainApp, favourite));
    }

	public void setRecentGroup() {
	    ObservableList<Group> recentGroup = FXCollections.observableArrayList();

        try {
            NetworkResponse networkGetUserGroup = WebService.get("http://51.255.196.182:3000/group?user_uid=" + connectedUser.getId(), mainApp.getRequestHeader());

            JSONArray jsonGroups = new JSONArray(networkGetUserGroup.getBody());
            for(int i = jsonGroups.length()-1; i > jsonGroups.length()-4; i--) {
                if (i < 0) {
                    break;
                }
                recentGroup.add(new Group(jsonGroups.getJSONObject(i)));
            }

            Platform.runLater(() -> groupTable.setItems(recentGroup));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAddTo(Product selectedProduct) {
        int nbProduct = numberChoiceBox.getValue();
        favourite.addProduct(selectedProduct, nbProduct);

        //save in DB
        //TODO

        //to delete

        reminderTable.getItems().remove(selectedProduct);
        setFavouriteBasketTotal();
    }

    @FXML
    private void handleChangeToGroupManagement() {
        mainApp.changeLayoutToGroupManagement();
    }

}
