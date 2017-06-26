package themePlugin.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.InputStream;

public class CssThemeFile {

    private InputStream css;
    private File cssDefault;
    private StringProperty name;
    private StringProperty creator;
    private StringProperty version;
    private ImageView themeImg;

    public CssThemeFile(InputStream css, String name, String creator, String version) {
        this.css = css;
        this.name = new SimpleStringProperty(name);
        this.creator = new SimpleStringProperty(creator);
        this.version = new SimpleStringProperty(version);
    }

    public CssThemeFile(File cssDefault, String name, String creator, String version) {
        this.cssDefault = cssDefault;
        this.name = new SimpleStringProperty(name);
        this.creator = new SimpleStringProperty(creator);
        this.version = new SimpleStringProperty(version);
    }

    public InputStream getCss() {
        return css;
    }

    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this, "name");
        return name;
    }

    public StringProperty creatorProperty() {
        if (creator == null) creator = new SimpleStringProperty(this, "creator");
        return creator;
    }

    public StringProperty versionProperty() {
        if (version == null) version = new SimpleStringProperty(this, "version");
        return version;
    }

    public ImageView getThemeImg() {
        return themeImg;
    }

    public void setThemeImg(Image themeImg) {
        this.themeImg = new ImageView(themeImg);
        this.themeImg.setFitHeight(60);
        this.themeImg.setFitWidth(60);
    }

    public String getName() {
        return name.get();
    }
}
