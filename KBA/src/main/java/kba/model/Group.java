package kba.model;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Group {
	
	private StringProperty name;
	private ObservableList<User> users = FXCollections.observableArrayList();
	private ImageView imageGroup;
	private User creator;

    public Group() {
        this.name = new SimpleStringProperty();
        this.imageGroup = new ImageView();
        this.imageGroup.setFitHeight(60);
        this.imageGroup.setFitWidth(60);
        this.creator = new User();
    }

	public Group(String name, Image imageGroup, User creator) {
		this.name = new SimpleStringProperty(name);
		this.imageGroup = new ImageView(imageGroup);
		this.imageGroup.setFitHeight(60);
		this.imageGroup.setFitWidth(60);
		this.creator = new User(creator);
	}

    public Group(String name, User user, Image imageGroup, User creator) {
        this.name = new SimpleStringProperty(name);
        users.add(user);
		this.imageGroup = new ImageView(imageGroup);
		this.imageGroup.setFitHeight(60);
		this.imageGroup.setFitWidth(60);
		this.creator = new User(creator);
    }

	public Group(String name, List<User> users, Image imageGroup, User creator) {
		this.name = new SimpleStringProperty(name);
		this.users = (ObservableList<User>) users;
		this.imageGroup = new ImageView(imageGroup);
		this.imageGroup.setFitHeight(60);
		this.imageGroup.setFitWidth(60);
		this.creator = new User(creator);
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

	public ObservableList<User> getUsers() {
		return users;
	}

	public ImageView getImageGroup() {
		return imageGroup;
	}

	public void setImageGroup(Image imageGroup) {
		this.imageGroup = new ImageView(imageGroup);
		this.imageGroup.setFitHeight(60);
		this.imageGroup.setFitWidth(60);
	}
	
	public void addUserToGroup(User user) {
		users.add(user);
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User user) {
        this.creator = user;
    }

	public String toString() {
		return name.getValue();
	}
}
