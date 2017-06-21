package kba.view.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kba.MainApp;
import kba.model.Group;
import kba.model.User;

public class MemberManagementDialogController {

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
    private Button removeButton;
    @FXML
    private Button addMemberButton;

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

    public void setDataInTableByGroup(Group referencedGroup) {
        this.referencedGroup = referencedGroup;

        memberTable.setItems(referencedGroup.getUsers());
    }

    public void setButton(User selectedUser) {
        detailButton.setOnAction(lambda->{
            if (selectedUser != null) {
                mainApp.showUserDetailDialog(selectedUser);
            }
        });
        removeButton.setOnAction(lambda->{
            if (selectedUser != null) {
                //must be tested on the id
                //TODO
                if (selectedUser.getId().equals(connectedUser.getId())) {
                    // Show the error message.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(dialogStage);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Vous ne pouvez pas vous retirer du groupe ici");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/style/MainTheme.css").toExternalForm());
                    dialogPane.getStyleClass().add("myDialog");

                    alert.showAndWait();
                } else {
                    referencedGroup.getUsers().remove(selectedUser);
                }

                //save in DB
                //TODO
            }
        });
    }

    @FXML
    private void handleAddMember() {
        mainApp.showUserSearchDialog(referencedGroup);
        setDataInTableByGroup(referencedGroup);
    }

    @FXML
    private void handleReturn() {
        dialogStage.close();
    }
}
