package kba.view.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import kba.MainApp;
import kba.PluginSignature;
import kba.model.PluginHolder;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class PluginDialogController {

    private Stage dialogStage;
    private MainApp mainApp;

    @FXML
    private TableView<PluginHolder> pluginTable;
    @FXML
    private TableColumn<PluginHolder, String> pluginNameColumn;
    @FXML
    private TableColumn<PluginHolder, String> pluginAutorColumn;
    @FXML
    private TableColumn<PluginHolder, Integer> pluginActualVersionColumn;
    @FXML
    private TableColumn<PluginHolder, Integer> pluginRequireVersionColumn;
    @FXML
    private Button deleteButton;

	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        pluginNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        pluginAutorColumn.setCellValueFactory(cellData -> cellData.getValue().autorProperty());
        pluginActualVersionColumn.setCellValueFactory(cellData -> cellData.getValue().pluginVersionProperty().asObject());
        pluginRequireVersionColumn.setCellValueFactory(cellData -> cellData.getValue().requireVersionProperty().asObject());

        pluginTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedPlugin) -> deleteButton.setOnAction(lambda-> deletePlugin(selectedPlugin)));

    }

    public void setDataInTable() {
        ObservableList<PluginHolder> pluginData = FXCollections.observableArrayList(mainApp.getPluginList().values());
        pluginTable.setItems(pluginData);
    }

    private void deletePlugin(PluginHolder selectedPlugin) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Suppression de plugin");
        alert.setHeaderText("Attention");
        alert.setContentText("Etes-vous sur de vouloir supprimer ce plugin?");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
        dialogPane.getStyleClass().add("myDialog");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                for(Map.Entry<String , PluginHolder> entry : mainApp.getPluginList().entrySet()) {
                    String key = entry.getKey();
                    PluginHolder value = entry.getValue();

                    if (value.equals(selectedPlugin)) {
                        mainApp.getPluginList().remove(key);
                    }
                }
                File pluginToDelete = new File(System.getProperty("user.home")+"/KBA/Plugin/"+selectedPlugin.getJarFile());
                pluginToDelete.delete();
                setDataInTable();
            }
        });
    }

    @FXML
	private void handleReturn() {
		dialogStage.close();
	}
	
	@FXML
	private void handleMorePlugin() {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(dialogStage);
        alert.setTitle("Plus de plugin");
        alert.setHeaderText("Trouvez plus de plugin sur le site :");
        alert.setContentText("adresse du site");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(mainApp.getCurrentCss().toURI().toString());
        dialogPane.getStyleClass().add("myDialog");
        alert.showAndWait();
	}
}
