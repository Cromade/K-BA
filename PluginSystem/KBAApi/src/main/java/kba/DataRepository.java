package kba;

import java.util.List;

public interface DataRepository {

	public List<String> getProperties();
	
	public void addProperty(String newProp);
	
}
