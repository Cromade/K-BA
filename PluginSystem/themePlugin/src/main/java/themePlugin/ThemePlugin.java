package themePlugin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kba.DataRepository;
import kba.Plugin;
import kba.PluginSignature;
import themePlugin.model.CssThemeFile;
import themePlugin.utils.InitThemes;
import themePlugin.views.ThemeSelectorDialogController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Plugin(name = "Themes", creatorName = "K-BA", version = 1, requiresCoreVersion = 1, testedWithCoreVersion = 1)
public class ThemePlugin implements PluginSignature {

    private DataRepository dataRepository;
    private File currentCss;
    private String currentCssName;
    private Scene currentDialogScene;
    private ObservableList<CssThemeFile> themeData;
    private File newCss;

    /**
     * init the plugin
     */
    @Override
    public void init(DataRepository dataRepository) {

        this.themeData = FXCollections.observableArrayList(InitThemes.defaultInitTheme().initAllThemeData());
        this.dataRepository = dataRepository;
        this.currentCss = dataRepository.getDefaultCssFile();
        this.currentCssName = "Theme par default";

        try {
            this.newCss = File.createTempFile("javafx_stylesheet", "");
            this.newCss.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File themeApply = new File(new File(System.getProperty("user.home")), "KBA/theme.properties");
        if (themeApply.exists()) {
            //try auto close file reader & bf (java8)
            try (FileReader reader = new FileReader(themeApply); BufferedReader br = new BufferedReader(reader)) {
                String themeName = br.readLine();

                if (!themeName.equals("default")) {
                    for (CssThemeFile cssThemeFile : themeData){
                        if (cssThemeFile.getName().equals(themeName)) {
                            changeThemeAtStart(cssThemeFile);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // default theme
            themeApply.getParentFile().mkdirs();
            //auto close file writer (java8)
            try (FileWriter writer = new FileWriter(themeApply)) {
                writer.write("default");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * run the plugin
     */
    @Override
    public void run() {
        showThemeSelectionDialog();
    }

    /**
     * Show the theme selection Dialog
     */
    public void showThemeSelectionDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ThemePlugin.class.getResource("/views/ThemeSelectorDialog.fxml"));
            loader.setController(new ThemeSelectorDialogController());
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Selection d'un theme");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(dataRepository.getPrimaryStage());
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            currentDialogScene = scene;
            dialogStage.setScene(currentDialogScene);

            ThemeSelectorDialogController controller = loader.getController();
            controller.setThemePlugin(this);
            controller.setDialogStage(dialogStage);
            controller.setDataInTable();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * handle the change of theme
     * @param cssThemeFile
     */
    public void changeTheme(CssThemeFile cssThemeFile) {
        try {
            this.newCss.delete();
            this.newCss = File.createTempFile("javafx_stylesheet", "");
            Files.copy(cssThemeFile.getCss(), newCss.toPath(), StandardCopyOption.REPLACE_EXISTING);
            dataRepository.setNewCss(newCss);
            currentCss = newCss;
            currentCssName = cssThemeFile.getName();
            currentDialogScene.getStylesheets().add(currentCss.toURI().toString());
            PrintWriter writer = new PrintWriter(new File(new File(System.getProperty("user.home")), "KBA/theme.properties"));
            writer.print(cssThemeFile.getName());
            writer.close();

            //reload the themes because of the copy
            this.themeData = FXCollections.observableArrayList(InitThemes.defaultInitTheme().initAllThemeData());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * handle the change of theme
     * @param cssThemeFile
     */
    public void changeThemeAtStart(CssThemeFile cssThemeFile) {
        try {
            this.newCss.delete();
            this.newCss = File.createTempFile("javafx_stylesheet", "");
            Files.copy(cssThemeFile.getCss(), newCss.toPath(), StandardCopyOption.REPLACE_EXISTING);
            dataRepository.setNewCss(newCss);
            currentCss = newCss;
            currentCssName = cssThemeFile.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reset the theme
     */
    public void resetToDefault() {
        try {
            dataRepository.resetDefaultCss();
            currentCss = dataRepository.getDefaultCssFile();
            currentCssName = "Theme par default";
            currentDialogScene.getStylesheets().add(currentCss.toURI().toString());
            PrintWriter writer = new PrintWriter(new File(new File(System.getProperty("user.home")), "KBA/theme.properties"));
            writer.print("default");
            writer.close();

            //reload the themes because of the copy
            this.themeData = FXCollections.observableArrayList(InitThemes.defaultInitTheme().initAllThemeData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentCssName() {
        return currentCssName;
    }

    public ObservableList<CssThemeFile> getThemeData() {
        return themeData;
    }
}
