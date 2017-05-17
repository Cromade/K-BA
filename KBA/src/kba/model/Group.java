package kba.model;

import java.util.List;

import javafx.scene.image.Image;

public class Group {
	
	private String name;
	private List<User> users;
	private Image imageGroup;
	
	public Group(String name, List<User> users, Image imageGroup) {
		this.name = name;
		this.users = users;
		this.imageGroup = imageGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
