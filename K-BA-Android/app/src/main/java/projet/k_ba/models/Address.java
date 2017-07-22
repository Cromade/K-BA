package projet.k_ba.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by candice on 16/07/2017.
 */
public class Address implements Parcelable{

    public Address(JSONObject obj) throws JSONException {
        if(obj != null) {
            this.uid = obj.getString("uid");
            this.address = obj.getString("address");
            this.zipcode = obj.getString("zipcode");
            this.city = obj.getString("city");
        }
    }

    private String uid;
    private String address;
    private String zipcode;
    private String city;

    public Address() {

    };

    public Address(String uid, String address, String zipcode, String city) {
        this.uid = uid;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
    }

    protected Address(Parcel in) {
        uid = in.readString();
        address = in.readString();
        zipcode = in.readString();
        city = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(address);
        dest.writeString(zipcode);
        dest.writeString(city);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
