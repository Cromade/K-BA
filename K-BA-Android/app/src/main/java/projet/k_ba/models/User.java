package projet.k_ba.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by candice on 16/07/2017.
 */
public class User implements Parcelable{

    private String uid;
    private String firstname;
    private String lastname;
    private String pseudo;
    private String email;
    private String password;
    private String birthdate;
    private Address address;

    public User() {

    };

    public User(String uid, String firstname, String lastname, String pseudo, String email, String password, String birthdate, Address address) {
        this.uid = uid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.address = address;
    }

    protected User(Parcel in) {
        uid = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        pseudo = in.readString();
        email = in.readString();
        password = in.readString();
        birthdate = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(pseudo);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(birthdate);
        dest.writeParcelable(address, flags);
    }

    public User(JSONObject obj) throws JSONException{
        this.uid = obj.getString("uid");
        this.firstname = obj.getString("firstname");
        this.lastname = obj.getString("lastname");
        this.pseudo = obj.getString("pseudo");
        this.email = obj.optString("email");
        this.birthdate = obj.optString("birthdate");
        this.address = new Address(obj.optJSONObject("address"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", address=" + address +
                '}';
    }
}
