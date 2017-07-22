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
import projet.k_ba.models.User;

/**
 * Created by candice on 22/07/2017.
 */
public class ItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> items;

    public ItemAdapter(Context context, ArrayList<Item> items) {
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
        ItemHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cell
                    , parent, false);
            holder = new ItemHolder();
            holder.name = (TextView) convertView.findViewById(R.id.item_cell_name);
            holder.price = (TextView) convertView.findViewById(R.id.item_cell_price);
            holder.manufacturer = (TextView) convertView.findViewById(R.id.item_cell_manufacturer);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }
        fillCell(holder, items.get(position), position);
        return convertView;
    }


    private void fillCell(ItemHolder holder, Item item, final int position) {
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice() + "€");
        holder.manufacturer.setText(item.getManufacturer());
    }

    static class ItemHolder {
        TextView name;
        TextView price;
        TextView manufacturer;
    }
}
