import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PluginParser {

    public static void PluginParser() throws IOException {
        String pluginDirectoryStr = "plugin";

        File pluginDirectory = new File(pluginDirectoryStr);
        System.out.println("Will load plugin from folder : " + pluginDirectory.getAbsolutePath());

        if (!pluginDirectory.exists()) {
            System.out.println("Plugin directory do not exist. Stopping.");
            return;
        }

        if (!pluginDirectory.isDirectory()) {
            System.out.println("Plugin directory is not a folder. Stopping.");
            return;
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

        shellTestPluginParser(pluginRepository);
    }

    public static void analyzeJar(File jarFile, Map<String, PluginHolder> pluginRepository) throws IOException {

        if (pluginRepository.values().stream().filter(ph -> ph.jarFile.equals(jarFile.getName())).count() > 0) {
            // already analyzed
            return;
        }

        //System.out.println("Found plugin : " + jarFile.getName());
        URLClassLoader pluginClassLoader = new URLClassLoader(new URL[] {jarFile.toURI().toURL()});
        String mainClassName = null;

        URL manifestUrl = pluginClassLoader.findResource("META-INF/MANIFEST.MF");
//		System.out.println(manifestUrl);
        if (manifestUrl == null || !manifestUrl.toString().contains(jarFile.getAbsolutePath())) {
//			System.out.println("plugin do not seem to contains a META-INF/MANIFEST.MF file.");
        }

        try (InputStream is = manifestUrl.openStream();
             LineNumberReader reader = is == null ? null : new LineNumberReader(new InputStreamReader(is, "UTF-8"))) {

            if (reader == null) {
//				System.out.println("plugin do not seem to contains a META-INF/MANIFEST.MF file.");
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

        if (mainClassName != null) {
            System.out.println("Found main plugin class : " + mainClassName);
        } else {
            System.out.println("Plugin is not declaring a main plugin class. Ignoring it.");
        }

        Class<? extends PluginSignature> pluginClass;
        try {
            pluginClass = Class.forName(mainClassName, true, pluginClassLoader).asSubclass(PluginSignature.class);
        } catch (ClassNotFoundException e) {
            System.out.println("class not found error :" + e.getMessage());
            return;
        }

        Plugin pluginAnnotation = pluginClass.getAnnotation(Plugin.class);
        if (pluginAnnotation == null) {
            System.out.println("Main plugin class is not declaring an @Plugin annotation.");
            return;
        }

        System.out.println("plugin name is : " + pluginAnnotation.name());
        pluginRepository.put(pluginAnnotation.name(), new PluginHolder(pluginAnnotation.name(), jarFile.getName(), pluginClass));
    }

    public static void shellTestPluginParser(Map<String, PluginHolder> pluginRepository) throws IOException {
        String inputline;
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(System.in, "UTF-8"));

        DataRepository dataRepository = new DataRepository() {

            private List<String> data = new ArrayList<>(Arrays.asList("a", "b", "c"));

            @Override
            public List<String> getProperties() {
                return Collections.unmodifiableList(data);
            }

            @Override
            public void addProperty(String newProp) {
                data.add(newProp);
            }
        };

        while((inputline = reader.readLine()) != null) {
            if ("exit".equals(inputline)) {
                return;
            }
            if ("list".equals(inputline)) {
                if (pluginRepository.isEmpty()) {
                    System.out.println("No plugin loaded yet.");
                } else {
                    pluginRepository.keySet().forEach(System.out::println);
                }
            }
            if (inputline.startsWith("execute ")) {
                String[] tokens = inputline.split(" ");
                PluginHolder pluginHolder = pluginRepository.get(tokens[1]);
                if (pluginHolder == null) {
                    System.out.println("plugin [" + tokens[1] + "] not found.");
                    continue;
                }
                try {
                    PluginSignature plugin = pluginHolder.pluginClass.newInstance();
                    plugin.init(dataRepository);
                    Method method = pluginHolder.pluginClass.getMethod(tokens[2]);
                    Object returnedValue = method.invoke(plugin);
                    System.out.println(returnedValue);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String methodName = inputline.substring("execute ".length());
            }
        }
    }
}
