package projet.k_ba.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by candice on 16/07/2017.
 */
public class Item implements Parcelable{

    private String uid;
    private String name;
    private String description;
    private float price;
    private String manufacturer;
    public Item() {

    }

    public Item(String uid, float price, String name, String description, String manufacturer) {
        this.uid = uid;
        this.price = price;
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;

    }

    public Item(JSONObject obj)  throws JSONException{
        this.uid = obj.getString("uid");
        this.price = (float) obj.getDouble("price");
        this.name = obj.getString("name");
        this.description = obj.getString("description");
        this.manufacturer = obj.getString("manufacturer");

    }

    protected Item(Parcel in) {
        uid = in.readString();
        name = in.readString();
        description = in.readString();
        price = in.readFloat();
        manufacturer = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeFloat(price);
        dest.writeString(manufacturer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
