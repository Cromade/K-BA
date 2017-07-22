package projet.k_ba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projet.k_ba.R;
import projet.k_ba.models.Group;

/**
 * Created by candice on 21/07/2017.
 */
public class GroupAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Group> groups;

    public GroupAdapter(Context context, ArrayList<Group> groups) {
        super();
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.group_cell, parent, false);
            holder = new GroupHolder();
            holder.groupName = (TextView) convertView.findViewById(R.id.group_cell_name);
            holder.groupOwner = (TextView) convertView.findViewById(R.id.group_cell_owner);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        fillCell(holder, groups.get(position), position);
        return convertView;
    }


    private void fillCell(GroupHolder holder, Group group, final int position) {
        holder.groupOwner.setText(group.getOwner().getPseudo());
        holder.groupName.setText(group.getName());
    }


    static class GroupHolder {
        TextView groupOwner;
        TextView groupName;
    }
}
