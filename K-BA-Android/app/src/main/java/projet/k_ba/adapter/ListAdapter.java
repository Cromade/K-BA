package projet.k_ba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projet.k_ba.R;
import projet.k_ba.models.List;

/**
 * Created by candice on 23/07/2017.
 */
public class ListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<List> lists;

    public ListAdapter(Context context, ArrayList<List> lists) {
        super();
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_cell, parent, false);
            holder = new ListHolder();
            convertView.setTag(holder);
        } else {
            holder = (ListHolder) convertView.getTag();
        }
        fillCell(holder, lists.get(position), position);
        return convertView;
    }


    private void fillCell(ListHolder holder, List list, final int position) {
        holder.listStatus.setText(list.getState());
        holder.listName.setText(list.getName());
    }


    static class ListHolder {
        TextView listStatus;
        TextView listName;
    }
}
