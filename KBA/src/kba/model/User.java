package kba.model;

import javafx.scene.image.Image;

public class User {

	private Long id;
	private String lastname;
	private String firstname;
	private String username;
	private String email;
	private String password;
	private String birthday;
	private String address;
	private String city;
	private String postalCode;
	private Image profileImg;
	private Boolean isPremium;
	
	//Default constructor
	public User() {
		this.id = null;
		this.lastname = null;
		this.firstname = null;
		this.username = null;
		this.email = null;
		this.password = null;
		this.birthday = null;
		this.address = null;
		this.city = null;
		this.postalCode = null;
		this.isPremium = null;
	}

	public User(String lastname, String firstname, String username, String email,
			String password, String birthday, String address, String city, String postalCode) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.birthday = birthday;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.isPremium = false;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		if (password != null) {
			return password;
		} 
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

	public void setProfileImg(Image img) {
		this.profileImg = img;
	}

	public Boolean getIsPremium() {
		return isPremium;
	}

	public void setIsPremium(Boolean isPremium) {
		this.isPremium = isPremium;
	}
	
}
