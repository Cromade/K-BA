package projet.k_ba.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by candice on 16/07/2017.
 */
public class User implements Parcelable{
    protected User(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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

    private String uid;
    private String firstname;
    private String lastname;
    private String password;
    private Address address;

    public User() {

    };
    public User(String uid, String firstname, String lastname, String password, Address address) {
        this.uid = uid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.address = address;
    }

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
}
