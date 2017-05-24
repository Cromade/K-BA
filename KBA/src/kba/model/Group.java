package kba.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Group {
	
	private StringProperty name;
	private List<User> users;
	private Image imageGroup;

	public Group(String name, Image imageGroup) {
		this.name = new SimpleStringProperty(name);
		this.users = new ArrayList<User>();
		this.imageGroup = imageGroup;
	}

    public Group(String name, User user, Image imageGroup) {
        this.name = new SimpleStringProperty(name);
        this.users = new ArrayList<User>();
        users.add(user);
        this.imageGroup = imageGroup;
    }

	public Group(String name, List<User> users, Image imageGroup) {
		this.name = new SimpleStringProperty(name);
		this.users = users;
		this.imageGroup = imageGroup;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		if (name == null) name = new SimpleStringProperty(this, "name");
		return name;
	}

	public List<User> getUsers() {
		return users;
	}

	public Image getImageGroup() {
		return imageGroup;
	}

	public void setImageGroup(Image imageGroup) {
		this.imageGroup = imageGroup;
	}
	
	public void addUserToGroup(User user) {
		users.add(user);
	}
}
