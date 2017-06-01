package kba.view.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PluginDialogController {

    private Stage dialogStage;

	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
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
        dialogPane.getStylesheets().add(getClass().getResource("/style/MainTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.showAndWait();
	}
}
