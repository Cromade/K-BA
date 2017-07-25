package projet.k_ba.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import projet.k_ba.R;

import projet.k_ba.adapter.ItemAdapter;
import projet.k_ba.adapter.ItemListAdapter;
import projet.k_ba.item.DetailsItemActivity;
import projet.k_ba.models.Item;
import projet.k_ba.models.ItemList;
import projet.k_ba.models.List;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;


public class DetailsListActivity extends AppCompatActivity {
    private ListView itemListView;
    private String token;
    private List list;
    private TextView listName;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);

        findViewsById();

        this.token = getIntent().getStringExtra("token");
        this.list = getIntent().getParcelableExtra("list");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        AsyncWebServices.get("/list/" + list.getUid(), new INetworkListener() {
            @Override
            public void onComplete(NetworkResponse response) {
                if(response != null) {
                    try {
                        JSONObject jsonList = new JSONObject(response.getBody());
                        list = new List(jsonList);
                        itemListView.setAdapter(new ItemListAdapter(DetailsListActivity.this, list.getItems()));
                        listName.setText(list.getName());
                        float totalPrice = 0;
                        for(ItemList il : list.getItems()) {
                            totalPrice += il.getQuantity() * il.getItem().getPrice();
                        }
                        total.setText(totalPrice + " €");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, headers);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = ((ItemList)itemListView.getAdapter().getItem(position)).getItem();
                Intent detailsItemActivity = new Intent(DetailsListActivity.this, DetailsItemActivity.class);
                detailsItemActivity.putExtra("token", DetailsListActivity.this.token);
                detailsItemActivity.putExtra("item", item);
                startActivity(detailsItemActivity);
            }
        });
    }

    private void findViewsById() {
        itemListView =(ListView) findViewById(R.id.item_list_view);
        listName = (TextView) findViewById(R.id.list_name);
        total = (TextView) findViewById(R.id.total_list_res);

    }
}
