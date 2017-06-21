package kba.view.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kba.MainApp;
import kba.model.Group;
import kba.model.User;

public class UserSearchDialogController {

    @FXML
    private TableView<User> memberTable;
    @FXML
    private TableColumn<User, ImageView> imageColumn = new TableColumn<>("Images");
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private Button detailButton;
    @FXML
    private Button addButton;

    private MainApp mainApp;
    private Stage dialogStage;
    private Group referencedGroup;
    private User connectedUser;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }
    public void setReferencedGroup(Group referencedGroup) {
        this.referencedGroup = referencedGroup;
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {

        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageUser"));
        imageColumn.setMinWidth(70);
        imageColumn.setMaxWidth(70);
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        // listener
        memberTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedMember) -> setButton(selectedMember));
    }

    @FXML
    public void handleSearch() {
        //search in DB
        //TODO

        memberTable.setItems(mainApp.getUserData());
    }

    public void setButton(User selectedUser) {
        detailButton.setOnAction(lambda->{
            if (selectedUser != null) {
                mainApp.showUserDetailDialog(selectedUser);
            }
        });
        if (selectedUser != null) {
            if (referencedGroup.getUsers().contains(selectedUser)) {
                addButton.setDisable(true);
                addButton.setText("Déjà présent");
            } else {
                addButton.setDisable(false);
                addButton.setText("Ajouter au groupe");
                addButton.setOnAction(lambda -> referencedGroup.addUserToGroup(selectedUser));
                //save in DB
                //TODO
            }
        }
    }

    @FXML
    private void handleReturn() {
        dialogStage.close();
    }
}
