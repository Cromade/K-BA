package kba.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class User {

	private String uid;
	private String lastname;
	private String firstname;
	private StringProperty username;
	private StringProperty email;
	private String password;
	private String birthday;
	private String address;
	private String city;
	private String postalCode;
	private Image profileImg;
	private ImageView imageUser;
	
	//Default constructor
	public User() {
		this.uid = null;
		this.lastname = null;
		this.firstname = null;
		this.username = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.password = null;
		this.birthday = null;
		this.address = null;
		this.city = null;
		this.postalCode = null;
	}

	public User(User user) {
	    this.uid = uid;
		this.lastname = user.getLastname();
		this.firstname = user.getFirstname();
		this.username = new SimpleStringProperty(user.getUsername());
		this.email = new SimpleStringProperty(user.getEmail());
		this.password = user.getPassword();
		this.birthday = user.getBirthday();
		this.address = user.getAddress();
		this.city = user.getCity();
		this.postalCode = user.getPostalCode();
	}

	public User(String uid, String lastname, String firstname, String username, String email,
			String birthday, String address, String city, String postalCode) {
	    this.uid = uid;
		this.lastname = lastname;
		this.firstname = firstname;
		this.username = new SimpleStringProperty(username);
		this.email = new SimpleStringProperty(email);
		this.password = "";
		this.birthday = birthday;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
	}

    public User(String uid, String lastname, String firstname, String pseudo, String email, String birthdate) {
        this.uid = uid;
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = new SimpleStringProperty(pseudo);
        this.email = new SimpleStringProperty(email);
        this.password = "";
        this.birthday = birthdate;
        this.address = "";
        this.city = "";
        this.postalCode = "";
    }

    public String getId() {
		return uid;
	}

	public void setId(String uid) {
		this.uid = uid;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

    public StringProperty usernameProperty() {
        if (username == null) username = new SimpleStringProperty(this, "username");
        return username;
    }

	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username.set(username);
	}

	public StringProperty emailProperty() {
		if (email == null) email = new SimpleStringProperty(this, "email");
		return email;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Image getProfileImg() {
		return profileImg;
	}

    public ImageView getImageUser() {
        return imageUser;
    }

	public void setProfileImg(Image img) {
		this.profileImg = img;
        this.imageUser = new ImageView(img);
        this.imageUser.setFitHeight(60);
        this.imageUser.setFitWidth(60);
	}

}
