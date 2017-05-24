import java.util.Arrays;
import java.util.List;

@Plugin(name = "plugin1", version = 1, requiresCoreVersion = 1, testedWithCoreVersion = 1)
public class Plugin1 implements PluginSignature {

	private DataRepository repository;

	public String testPlugin() {
		repository.addProperty(Long.toHexString(System.currentTimeMillis()));
		return "this is a test " + Arrays.toString(repository.getProperties().toArray());
	}

	@Override
	public void init(DataRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run() {
		List<String> properties = repository.getProperties();
	}
	
}
