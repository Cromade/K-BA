package kba.util;

import kba.Plugin;
import kba.PluginSignature;
import kba.model.PluginHolder;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PluginParser {

    public static Map<String, PluginHolder> pluginParser() {
        String pluginDirectoryStr = "plugin";

        //load the plugin directory
        File pluginDirectory = new File(pluginDirectoryStr);

        //if the directory do not exist
        if (!pluginDirectory.exists()) {
            return new HashMap<>();
        }

        //if the directory is not a folder
        if (!pluginDirectory.isDirectory()) {
            return new HashMap<>();
        }

        Map<String, PluginHolder> pluginRepository = new ConcurrentHashMap<>();
        File[] jarFiles = pluginDirectory.listFiles((dir, filename) -> filename.endsWith(".jar"));
        for(File jarFile : jarFiles) {
            try {
                analyzeJar(jarFile, pluginRepository);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return pluginRepository;
    }

    public static void analyzeJar(File jarFile, Map<String, PluginHolder> pluginRepository) throws IOException {

        if (pluginRepository.values().stream().filter(ph -> ph.jarFile.equals(jarFile.getName())).count() > 0) {
            //plugin already analyzed
            return;
        }

        URLClassLoader pluginClassLoader = new URLClassLoader(new URL[] {jarFile.toURI().toURL()});
        String mainClassName = null;

        //get the manifest and test it
        URL manifestUrl = pluginClassLoader.findResource("META-INF/MANIFEST.MF");
        if (manifestUrl == null || !manifestUrl.toString().contains(jarFile.getAbsolutePath())) {
			return;
        }

        try (InputStream is = manifestUrl.openStream();
             LineNumberReader reader = is == null ? null : new LineNumberReader(new InputStreamReader(is, "UTF-8"))) {

            //test the manifest reader/reading
            if (reader == null) {
                return;
            }

            String line;
            while((line = reader.readLine()) != null) {
                if (line.startsWith("Plugin-Main-Class:")) {
                    mainClassName = line.substring("Plugin-Main-Class:".length()).trim();
                }
                if (line.startsWith("Plugin-Main-Class:")) {
                    mainClassName = line.substring("Plugin-Main-Class:".length()).trim();
                }
            }
        }

        //test if the plugin have a main class
        if (mainClassName == null) {
            return;
        }

        Class<? extends PluginSignature> pluginClass;
        try {
            pluginClass = Class.forName(mainClassName, true, pluginClassLoader).asSubclass(PluginSignature.class);
        } catch (ClassNotFoundException e) {
            return;
        }

        //check if the plugin use the annotation
        Plugin pluginAnnotation = pluginClass.getAnnotation(Plugin.class);
        if (pluginAnnotation == null) {
            return;
        }

        pluginRepository.put(pluginAnnotation.name(), new PluginHolder(pluginAnnotation.name(), jarFile.getName(), pluginClass));
    }
}
