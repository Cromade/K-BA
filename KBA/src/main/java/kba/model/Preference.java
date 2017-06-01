package kba.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Preference {

	private Long id;
	private User owner;
	private ObservableList<Product> preferenceList;
	
	public Preference(Long id, User owner) {
		this.id = id;
		this.owner = owner;
		this.preferenceList = FXCollections.observableArrayList();
	}

	public Preference() {
		this.id = null;
		this.owner = null;
		this.preferenceList = FXCollections.observableArrayList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public ObservableList<Product> getPreferenceList() {
		return preferenceList;
	}

	public boolean addPreferenceList(Product productToAdd) {
		if (preferenceList != null) {
			for (Product product : preferenceList) {
				if (product.equals(productToAdd)) {
					return true;
				}
			}
		}
		preferenceList.add(productToAdd);
		return false;
	}
	
}
