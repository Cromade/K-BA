package kba.util;

import kba.DataRepository;
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

    public static Map<String, PluginHolder> pluginParser(DataRepository dataRepository) {

        //load the plugin directory
        File pluginDirectory = new File(new File(System.getProperty("user.home")), "KBA/Plugin");

        //if the directory do not exist
        if (!pluginDirectory.exists()) {
            pluginDirectory.getParentFile().mkdirs();
            pluginDirectory.mkdirs();
        }

        //if the directory is not a folder
        if (!pluginDirectory.isDirectory()) {
            return new HashMap<>();
        }

        Map<String, PluginHolder> pluginRepository = new ConcurrentHashMap<>();
        File[] jarFiles = pluginDirectory.listFiles((dir, filename) -> filename.endsWith(".jar"));
        for(File jarFile : jarFiles) {
            try {
                analyzeJar(jarFile, pluginRepository, dataRepository);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return pluginRepository;
    }

    public static void analyzeJar(File jarFile, Map<String, PluginHolder> pluginRepository, DataRepository dataRepository) throws IOException {

        if (pluginRepository.values().stream().filter(ph -> ph.jarFile.equals(jarFile.getName())).count() > 0) {
            //plugin already analyzed
            return;
        }

        URLClassLoader pluginClassLoader = new URLClassLoader(new URL[] {jarFile.toURI().toURL()});
        String mainClassName = null;

        //get the manifest and test it
        URL manifestUrl = pluginClassLoader.findResource("META-INF/MANIFEST.MF");
        if (manifestUrl == null || !manifestUrl.toString().contains(jarFile.getAbsolutePath())) {
            System.out.println("no manifest");
            return;
        }

        try (InputStream is = manifestUrl.openStream();
             LineNumberReader reader = is == null ? null : new LineNumberReader(new InputStreamReader(is, "UTF-8"))) {

            //test the manifest reader/reading
            if (reader == null) {
                System.out.println("manifest unreadable");
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
            System.out.println("no main class");
            return;
        }

        Class<? extends PluginSignature> pluginClass;
        try {
            pluginClass = Class.forName(mainClassName, true, pluginClassLoader).asSubclass(PluginSignature.class);
        } catch (ClassNotFoundException e) {
            System.out.println("error on main grab");
            return;
        }

        //check if the plugin use the annotation
        Plugin pluginAnnotation = pluginClass.getAnnotation(Plugin.class);
        if (pluginAnnotation == null) {
            System.out.println("no annotation");
            return;
        }

        PluginHolder pluginHolder = new PluginHolder(pluginAnnotation.name(), jarFile.getName(), pluginClass);
        PluginSignature pluginSignature;
        try {
            pluginSignature = pluginHolder.pluginClass.newInstance();
            pluginSignature.init(dataRepository);
            pluginHolder.setPlugin(pluginSignature);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        pluginRepository.put(pluginAnnotation.name(), pluginHolder);
    }
}
