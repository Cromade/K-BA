package kba.view.layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import kba.MainApp;
import kba.model.Basket;
import kba.model.BasketProduct;
import kba.model.Group;
import kba.model.Product;
import kba.util.ReminderProduct;

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
	private ObservableList<Basket> baskets;
	private Basket favourite;

    @FXML
    private void initialize() {
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
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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
	    //get in db the three last group made
        //TODO
        //to delete
        List<Group> groups = mainApp.getGroupData();
        int cmp = 0;
        for (Group group : groups) {
            recentGroup.add(group);
            cmp++;
            if(cmp==3) break;
        }

        groupTable.setItems(recentGroup);
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
