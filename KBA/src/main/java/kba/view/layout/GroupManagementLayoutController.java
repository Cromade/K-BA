package kba.view.layout;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import kba.MainApp;
import kba.model.Group;
import kba.model.User;
import kba.network.NetworkResponse;
import kba.network.WebService;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupManagementLayoutController {

	@FXML
	private TextField researchField;
	@FXML
	private TableView<Group> groupTable;
	@FXML
	private TableColumn<Group, ImageView> imageColumn = new TableColumn<Group, ImageView>("Images");
	@FXML
	private TableColumn<Group, String> nameColumn;
	@FXML
	private TableColumn<Group, String> creatorColumn;
	@FXML
    private Button modifyButton;
	@FXML
    private Button deleteButton;
	@FXML
    private Button memberButton;

	private MainApp mainApp;
	private User connectedUser;
    private ObservableList<Group> groupData = FXCollections.observableArrayList();

	@FXML
	private void initialize() {

        Platform.runLater(() -> {
            imageColumn.setCellValueFactory(new PropertyValueFactory<Group, ImageView>("imageGroup"));
            imageColumn.setMinWidth(70);
            imageColumn.setMaxWidth(70);
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            creatorColumn.setCellValueFactory(cellData -> cellData.getValue().getCreator().usernameProperty());

            // listener
            groupTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, selectedProduct) -> setButton(selectedProduct));
        });

	}

	public void setDataInTable() {
        try {
            NetworkResponse networkGetUserGroup = WebService.get("http://51.255.196.182:3000/group?user_uid=" + connectedUser.getId(), mainApp.getRequestHeader());

            JSONArray jsonGroups = new JSONArray(networkGetUserGroup.getBody());
            for(int i = 0; i < jsonGroups.length(); i++) {
                groupData.add(new Group(jsonGroups.getJSONObject(i)));
            }

            Platform.runLater(() -> {
                groupTable.setItems(groupData);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setConnectedUser(User user) {
	    this.connectedUser = user;
    }

    @FXML
    private void handleNewGroup() {
	    Group tempGroup = new Group();
        boolean okClicked = mainApp.showGroupEditDialog(tempGroup, true);
        if (okClicked) {
            Map<String, String> params = new HashMap<>();
            params.put("name", tempGroup.getName());

            try {
                NetworkResponse networkConnexionResponse = WebService.post("http://51.255.196.182:3000/group", params, mainApp.getRequestHeader());
            }catch (Exception e) {

                if (e.getLocalizedMessage().contains("401 for URL")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur lors de la sauvegarde");
                    alert.setContentText("Veuillez réessayer...");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
                    dialogPane.getStyleClass().add("myDialog");
                    alert.showAndWait();
                }
            }

            //refresh the group layout
            setDataInTable();
        }
    }

    @FXML
    private void handleResearch() {
	    //TODO
    }

    public void setButton(Group selectedGroup) {
        modifyButton.setOnAction(lambda->{
            if (selectedGroup != null) {
                boolean okClicked = mainApp.showGroupEditDialog(selectedGroup, false);
                if (okClicked) {
                    // save changes
                    mainApp.setGroupData(groupData);
                    //refresh the group layout
                    setDataInTable();
                }
            }
        });
        deleteButton.setOnAction(lambda->{
            if (selectedGroup != null) {
                //test the owner of the group
                if (selectedGroup.getCreator().equals(connectedUser)) {
                    //remove in DB
                    groupData.remove(selectedGroup);
                } else {
                    //remove from group
                    selectedGroup.getUsers().remove(connectedUser);
                }
                //save in DB
                //TODO
                mainApp.setGroupData(groupData);
            }
        });
        memberButton.setOnAction(lambda->{
            if (selectedGroup != null) {
                mainApp.showMemberManagementDialog(selectedGroup);
            }
        });
    }

}
