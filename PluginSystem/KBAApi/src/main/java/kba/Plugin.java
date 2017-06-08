package kba;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Plugin {

	// id of the plugin
	public String name();

	// creator of the plugin
	public String creatorName();
	
	// plugin version
	public int version();
	
	// min version
	public int requiresCoreVersion();
	
	// max version suggested
	public int testedWithCoreVersion();
	
}
