package themePlugin.views;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import themePlugin.ThemePlugin;
import themePlugin.model.CssThemeFile;

public class ThemeSelectorDialogController {

    private Stage dialogStage;
    private ThemePlugin themePlugin;

    @FXML
    private TableView<CssThemeFile> themeTable;
    @FXML
    private TableColumn<CssThemeFile, ImageView> imageColumn = new TableColumn<CssThemeFile, ImageView>("Images");
    @FXML
    private TableColumn<CssThemeFile, String> themeNameColumn;
    @FXML
    private TableColumn<CssThemeFile, String> themeCreatorColumn;
    @FXML
    private TableColumn<CssThemeFile, String> themeVersionColumn;
    @FXML
    private Button chooseButton;
    @FXML
    private Label currentThemeLabel;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setThemePlugin(ThemePlugin themePlugin) {
        this.themePlugin = themePlugin;
    }

    @FXML
    private void initialize() {

        imageColumn.setCellValueFactory(new PropertyValueFactory<>("themeImg"));
        imageColumn.setMinWidth(70);
        imageColumn.setMaxWidth(70);
        themeNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        themeCreatorColumn.setCellValueFactory(cellData -> cellData.getValue().creatorProperty());
        themeVersionColumn.setCellValueFactory(cellData -> cellData.getValue().versionProperty());

        themeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedTheme) -> chooseButton.setOnAction(lambda-> {
                    themePlugin.changeTheme(selectedTheme);
                    currentThemeLabel.setText(selectedTheme.getName());
                    setDataInTable();
                }));

    }

    public void setDataInTable() {
        themeTable.setItems(themePlugin.getThemeData());
        currentThemeLabel.setText(themePlugin.getCurrentCssName());
    }

    @FXML
    private void handleReturn() {
        dialogStage.close();
    }

    @FXML
    private void handleReset() {
        themePlugin.resetToDefault();
        currentThemeLabel.setText(themePlugin.getCurrentCssName());
        setDataInTable();
    }
}
