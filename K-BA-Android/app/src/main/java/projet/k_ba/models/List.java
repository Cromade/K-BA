package projet.k_ba.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by candice on 16/07/2017.
 */
public class List implements Parcelable {

    private String uid;
    private String name;
    private String state;
    private ArrayList<ItemList> items;
    private String group_uid;

    protected List(Parcel in) {
        uid = in.readString();
        name = in.readString();
        state = in.readString();
        items = in.createTypedArrayList(ItemList.CREATOR);
        group_uid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(state);
        dest.writeTypedList(items);
        dest.writeString(group_uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<List> CREATOR = new Creator<List>() {
        @Override
        public List createFromParcel(Parcel in) {
            return new List(in);
        }

        @Override
        public List[] newArray(int size) {
            return new List[size];
        }
    };

    public List() {
    }

    public List(String uid, String name, String state, ArrayList<ItemList> items) {
        this.uid = uid;
        this.name = name;
        this.state = state;
        this.items = items;
    }

    public List(JSONObject obj) throws JSONException {
        this.uid = obj.getString("uid");
        this.name = obj.getString("name");
        this.state = obj.getString("state");
        JSONArray jsonItems = obj.optJSONArray("items");
        if(jsonItems != null) {
            ArrayList<ItemList> items = new ArrayList<ItemList>();
            for(int i = 0; i < jsonItems.length(); i++) {
                items.add(new ItemList(jsonItems.getJSONObject(i)));
            }
            this.items = items;

        }
    }
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<ItemList> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemList> items) {
        this.items = items;
    }

    public String getGroup_uid() {
        return group_uid;
    }

    public void setGroup_uid(String group_uid) {
        this.group_uid = group_uid;
    }
}
