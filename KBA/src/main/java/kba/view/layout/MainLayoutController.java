package kba.view.layout;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import kba.MainApp;
import kba.model.Basket;

public class MainLayoutController {

	@FXML
	private Label favouriteBasketTotalLabel;
	@FXML
	private Label favouriteBasketNameLabel;
	@FXML
    private Button favouriteBasketButton;
	
	private MainApp mainApp;
	private ObservableList<Basket> baskets;
	
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
	
	@FXML
	private void handleChangeToGroupManagement() {
		mainApp.changeLayoutToGroupManagement();
	}
}
