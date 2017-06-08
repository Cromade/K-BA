package kba.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kba.Plugin;
import kba.PluginSignature;

public class PluginHolder {

    public StringProperty name;
    public String jarFile;
    public Class<? extends PluginSignature> pluginClass;

    private StringProperty autor;
    private IntegerProperty pluginVersion;
    private IntegerProperty requireVersion;

    public PluginHolder(String name, String jarFile, Class<? extends PluginSignature> pluginClass) {
        super();
        this.name = new SimpleStringProperty(name);
        this.jarFile = jarFile;
        this.pluginClass = pluginClass;

        Plugin pluginAnnotation = pluginClass.getAnnotation(Plugin.class);
        this.autor = new SimpleStringProperty(pluginAnnotation.creatorName());
        this.pluginVersion = new SimpleIntegerProperty(pluginAnnotation.version());
        this.requireVersion = new SimpleIntegerProperty(pluginAnnotation.requiresCoreVersion());
    }

    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this, "name");
        return name;
    }

    public StringProperty autorProperty() {
        if (autor == null) autor = new SimpleStringProperty(this, "autor");
        return autor;
    }

    public IntegerProperty pluginVersionProperty() {
        if (pluginVersion == null) pluginVersion = new SimpleIntegerProperty(this, "1");
        return pluginVersion;
    }

    public IntegerProperty requireVersionProperty() {
        if (requireVersion == null) requireVersion = new SimpleIntegerProperty(this, "1");
        return requireVersion;
    }
}