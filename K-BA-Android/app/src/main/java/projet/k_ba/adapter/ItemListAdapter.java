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
import projet.k_ba.models.ItemList;

/**
 * Created by candice on 23/07/2017.
 */
public class ItemListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ItemList> items;

    public ItemListAdapter(Context context, ArrayList<ItemList> items) {
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
        ItemListHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_cell
                    , parent, false);
            holder = new ItemListHolder();
            holder.name = (TextView) convertView.findViewById(R.id.item_name);
            holder.price = (TextView) convertView.findViewById(R.id.item_price);
            holder.quantity = (TextView) convertView.findViewById(R.id.item_cell_quantity);
            holder.total = (TextView) convertView.findViewById(R.id.total_list_res);

            convertView.setTag(holder);
        } else {
            holder = (ItemListHolder) convertView.getTag();
        }
        fillCell(holder, items.get(position), position);
        return convertView;
    }


    private void fillCell(ItemListHolder holder, ItemList item, final int position) {
        holder.name.setText(item.getItem().getName());
        holder.price.setText(item.getItem().getPrice() + "€");
        holder.total.setText(item.getQuantity() * item.getItem().getPrice() + " €");
        holder.quantity.setText(item.getQuantity()+"");

    }

    static class ItemListHolder {
        TextView name;
        TextView price;
        TextView total;
        TextView quantity;

    }
}
