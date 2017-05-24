public class PluginHolder {

	public String name;
	public String jarFile; 
	public Class<? extends PluginSignature> pluginClass;

	public PluginHolder(String name, String jarFile, Class<? extends PluginSignature> pluginClass) {
		super();
		this.name = name;
		this.jarFile = jarFile;
		this.pluginClass = pluginClass;
	}
}
