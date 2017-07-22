package projet.k_ba.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projet.k_ba.R;
import projet.k_ba.models.Category;
import projet.k_ba.models.Item;
import projet.k_ba.models.List;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;

public class DetailsItemActivity extends AppCompatActivity {

    private String token;
    private Item item;
    private TextView itemName;
    private TextView itemDescription;
    private TextView itemPrice;
    private TextView itemManufacturer;
    private Spinner itemQuantity;
    private Spinner listChoice;
    private Button addToList;
    private Button addToPreference;
    private java.util.List<List> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item);

        findViewsById();

        this.token = getIntent().getStringExtra("token");
        this.item = getIntent().getParcelableExtra("item");

        itemName.setText(this.item.getName());
        itemDescription.setText(this.item.getDescription());
        itemPrice.setText(this.item.getPrice()+ " €");
        itemManufacturer.setText(this.item.getManufacturer());


        final Map<String, String> headers = new HashMap<>();
        headers.put("Authorization","Bearer " + this.token);
        AsyncWebServices.get("/list", new INetworkListener() {
            @Override
            public void onComplete(NetworkResponse response) {
                if(response != null) {
                    try {
                        JSONArray jsonLists = new JSONArray(response.getBody());
                        lists = new ArrayList<>();
                        java.util.List<String> spinnerData = new ArrayList<String>();
                        for(int i = 0; i < jsonLists.length(); i++) {
                            List l = new List(jsonLists.getJSONObject(i));
                            lists.add(l);
                            spinnerData.add(l.getName());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailsItemActivity.this, android.R.layout.simple_list_item_1, spinnerData);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        listChoice.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, headers);

    }


    public void findViewsById() {
        itemName = (TextView) findViewById(R.id.item_name);
        itemDescription = (TextView) findViewById(R.id.item_description);
        itemPrice = (TextView) findViewById(R.id.item_price);
        itemManufacturer = (TextView) findViewById(R.id.item_manufacturer);
        itemQuantity = (Spinner) findViewById(R.id.quantity_spinner);
        listChoice = (Spinner) findViewById(R.id.list_spinner);
        addToList = (Button) findViewById(R.id.add_list_button);
        addToPreference = (Button) findViewById(R.id.add_pref_button);



    }
}
