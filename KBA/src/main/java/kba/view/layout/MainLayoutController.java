package kba.view.layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import kba.MainApp;
import kba.model.Basket;
import kba.model.Group;

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
    private TableColumn<Group, ImageView> imageColumn = new TableColumn<Group, ImageView>("Images");
    @FXML
    private TableColumn<Group, String> nameColumn;
	
	private MainApp mainApp;
	private ObservableList<Basket> baskets;

    @FXML
    private void initialize() {

        imageColumn.setCellValueFactory(new PropertyValueFactory<Group, ImageView>("imageGroup"));
        imageColumn.setMinWidth(70);
        imageColumn.setMaxWidth(70);
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void setFavouriteBasketTotal() {
		Basket favourite = new Basket();
		//get in db the favourite
		//TODO
		//to delete
		baskets = mainApp.getBasketData();
		for (Basket basket :baskets) {
			if (basket.getIsFavourite() == true) {
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
        favouriteBasketButton.setOnAction(lambda->{
            mainApp.showBasketDetailDialog(finalFavourite);
        });
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
	
	@FXML
	private void handleChangeToGroupManagement() {
		mainApp.changeLayoutToGroupManagement();
	}
}
