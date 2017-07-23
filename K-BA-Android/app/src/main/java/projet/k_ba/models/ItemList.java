package projet.k_ba.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by candice on 23/07/2017.
 */
public class ItemList implements Parcelable{
    private Item item;
    private int quantity;

    public ItemList() {
    }

    public ItemList(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public ItemList(JSONObject obj) throws JSONException {
        this.item = new Item(obj.getJSONObject("item"));
        this.quantity = obj.getInt("quantity");
    }

    protected ItemList(Parcel in) {
        item = in.readParcelable(Item.class.getClassLoader());
        quantity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(item, flags);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemList> CREATOR = new Creator<ItemList>() {
        @Override
        public ItemList createFromParcel(Parcel in) {
            return new ItemList(in);
        }

        @Override
        public ItemList[] newArray(int size) {
            return new ItemList[size];
        }
    };

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ItemList{" +
                "item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
