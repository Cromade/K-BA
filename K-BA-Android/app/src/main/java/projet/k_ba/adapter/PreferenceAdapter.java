package projet.k_ba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projet.k_ba.R;
import projet.k_ba.models.Item;
import projet.k_ba.models.List;

/**
 * Created by candice on 25/07/2017.
 */
public class PreferenceAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Item> items;

        public PreferenceAdapter(Context context, ArrayList<Item> items) {
            super();
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PreferenceHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_cell, parent, false);
                holder = new PreferenceHolder();
                holder.itemPrice = (TextView) convertView.findViewById(R.id.list_cell_status);
                holder.itemName = (TextView) convertView.findViewById(R.id.list_cell_name);
                convertView.setTag(holder);
            } else {
                holder = (PreferenceHolder) convertView.getTag();
            }
            fillCell(holder, items.get(position), position);
            return convertView;
        }


        private void fillCell(PreferenceHolder holder, Item item, final int position) {
            holder.itemName.setText(item.getName());
            holder.itemPrice.setText(item.getPrice()+" €");
        }


        static class PreferenceHolder {
            TextView itemPrice ;
            TextView itemName;
        }
    }

