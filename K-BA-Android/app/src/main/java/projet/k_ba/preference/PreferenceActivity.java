package projet.k_ba.preference;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projet.k_ba.R;
import projet.k_ba.adapter.ListAdapter;
import projet.k_ba.adapter.PreferenceAdapter;
import projet.k_ba.item.DetailsItemActivity;
import projet.k_ba.list.AddListActivity;
import projet.k_ba.list.DetailsListActivity;
import projet.k_ba.models.Item;
import projet.k_ba.models.List;
import projet.k_ba.models.User;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;

public class PreferenceActivity extends AppCompatActivity {
    private ListView listPreference;
    private String token;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        findViewsById();

        token = getIntent().getStringExtra("token");
        user = getIntent().getParcelableExtra("user");

        this.listPreference.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = (Item) listPreference.getAdapter().getItem(position);
                Intent detailsItemActivity = new Intent(PreferenceActivity.this, DetailsItemActivity.class);
                detailsItemActivity.putExtra("item", selectedItem);
                detailsItemActivity.putExtra("token", PreferenceActivity.this.token);
                startActivity(detailsItemActivity);
            }
        });

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer "+ token);
        AsyncWebServices.get("/preference", new INetworkListener() {
            @Override
            public void onComplete(NetworkResponse response) {
                if (response != null) {
                    try {
                        JSONArray jsonItem = new JSONArray(response.getBody());
                        ArrayList<Item> items = new ArrayList<Item>();
                        for(int i = 0; i < jsonItem.length(); i++) {
                            items.add(new Item(jsonItem.getJSONObject(i)));
                        }
                        PreferenceAdapter preferenceAdapter = new PreferenceAdapter(PreferenceActivity.this, items);
                        PreferenceActivity.this.listPreference.setAdapter(preferenceAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        },headers);

    }

    private void findViewsById() {
        listPreference = (ListView) findViewById(R.id.list_list_view);
    }

}
