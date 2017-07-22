package projet.k_ba.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by candice on 21/07/2017.
 */
public class Group implements Parcelable {
    private String uid;
    private String name;
    private User owner;
    private ArrayList<List> lists;
    private ArrayList<User> members;


    protected Group(Parcel in) {
        uid = in.readString();
        name = in.readString();
        owner = in.readParcelable(User.class.getClassLoader());
        lists = in.createTypedArrayList(List.CREATOR);
        members = in.createTypedArrayList(User.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeParcelable(owner, flags);
        dest.writeTypedList(lists);
        dest.writeTypedList(members);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public Group() {
    }

    public Group(String uid, String name, User owner, ArrayList<List> lists, ArrayList<User> members) {
        this.uid = uid;
        this.name = name;
        this.owner = owner;
        this.lists = lists;
        this.members = members;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArrayList<List> getLists() {
        return lists;
    }

    public void setLists(ArrayList<List> lists) {
        this.lists = lists;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Group{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", lists=" + lists +
                ", members=" + members +
                '}';
    }
}
