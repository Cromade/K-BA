package projet.k_ba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projet.k_ba.R;
import projet.k_ba.models.User;

/**
 * Created by candice on 22/07/2017.
 */
public class UserAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<User> users;

    public UserAdapter(Context context, ArrayList<User> users) {
        super();
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_cell, parent, false);
            holder = new UserHolder();
            holder.pseudo = (TextView) convertView.findViewById(R.id.user_cell_pseudo);
            convertView.setTag(holder);
        } else {
            holder = (UserHolder) convertView.getTag();
        }
        fillCell(holder, users.get(position), position);
        return convertView;
    }


    private void fillCell(UserHolder holder, User user, final int position) {
        holder.pseudo.setText(user.getPseudo());
    }

    static class UserHolder {
        TextView pseudo;
    }
}
