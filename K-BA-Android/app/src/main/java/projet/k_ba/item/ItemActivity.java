package projet.k_ba.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projet.k_ba.R;
import projet.k_ba.adapter.ItemAdapter;
import projet.k_ba.group.AddMemberToGroupActivity;
import projet.k_ba.models.Category;
import projet.k_ba.models.Item;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;
import projet.k_ba.network.WebService;

public class ItemActivity extends AppCompatActivity {
    private Spinner spinnerItemCategories;
    private Button searchItemButton;
    private EditText searchItemEditText;
    private ListView itemListView;
    private String token;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        this.token = getIntent().getStringExtra("token");

        findViewsById();

        final Map<String, String> headers = new HashMap<>();
        headers.put("Authorization","Bearer " + this.token);
        AsyncWebServices.get("/category", new INetworkListener() {
            @Override
            public void onComplete(NetworkResponse response) {
                if(response != null) {
                    try {
                        JSONArray jsonCategories = new JSONArray(response.getBody());
                        categories = new ArrayList<>();
                        List<String> spinnerData = new ArrayList<String>();
                        spinnerData.add("Aucune catégorie");
                        for(int i = 0; i < jsonCategories.length(); i++) {
                            Category c = new Category(jsonCategories.getJSONObject(i));
                            categories.add(c);
                            spinnerData.add(c.getName());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ItemActivity.this, android.R.layout.simple_list_item_1, spinnerData);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerItemCategories.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, headers);

        searchItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = spinnerItemCategories.getSelectedItemPosition();
                String url = "/item";
                position--;
                if(position >= 0) {
                    url += "?category_uid=" + categories.get(position).getUid();
                }
                String search = searchItemEditText.getText().toString();
                if(search.length() > 0) {
                    if(position >= 0) {
                        url += "&";
                    } else {
                        url += "?";
                    }
                    url += "search=" + search;
                }
                AsyncWebServices.get(url, new INetworkListener() {
                    @Override
                    public void onComplete(NetworkResponse response) {
                        if(response != null) {
                            try {
                                JSONArray jsonItems = new JSONArray(response.getBody());
                                ArrayList<Item> items = new ArrayList<Item>();
                                for(int i = 0; i < jsonItems.length(); i++) {
                                    items.add(new Item(jsonItems.getJSONObject(i)));
                                }
                                itemListView.setAdapter(new ItemAdapter(ItemActivity.this, items));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, headers);
            }
        });

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsItemActivity = new Intent(ItemActivity.this, DetailsItemActivity.class);
                detailsItemActivity.putExtra("token", ItemActivity.this.token);
                detailsItemActivity.putExtra("item", (Item)ItemActivity.this.itemListView.getAdapter().getItem(position));
                startActivity(detailsItemActivity);
            }
        });
    }

    public void findViewsById() {
        spinnerItemCategories = (Spinner) findViewById(R.id.spinner_item_categories);
        searchItemButton = (Button) findViewById(R.id.search_item_button);
        searchItemEditText = (EditText) findViewById(R.id.edit_text_search_item);
        itemListView = (ListView) findViewById(R.id.item_list_view);

    }
}
