package kba.view.layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import kba.MainApp;
import kba.model.Group;
import kba.model.User;

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

		imageColumn.setCellValueFactory(new PropertyValueFactory<Group, ImageView>("imageGroup"));
		imageColumn.setMinWidth(70);
		imageColumn.setMaxWidth(70);
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		creatorColumn.setCellValueFactory(cellData -> cellData.getValue().getCreator().usernameProperty());

        // listener
        groupTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedProduct) -> setButton(selectedProduct));
	}

	public void setDataInTable() {
		//get the preference of the user in db
		//TODO

		//to delete when db up
		groupData = mainApp.getGroupData();
		groupTable.setItems(groupData);
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
            // save changes
            //TODO
            tempGroup.setCreator(connectedUser);
            groupData.add(tempGroup);
            mainApp.setGroupData(groupData);

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
