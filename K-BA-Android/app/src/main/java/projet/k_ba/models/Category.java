package projet.k_ba.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by candice on 22/07/2017.
 */
public class Category implements Parcelable {
    private String name;
    private String uid;

    public Category() {
    }

    public Category(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    protected Category(Parcel in) {
        name = in.readString();
        uid = in.readString();
    }

    public Category(JSONObject obj)throws JSONException {
        this.uid = obj.getString("uid");
        this.name = obj.getString("name");
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
