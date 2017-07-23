package projet.k_ba.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        final java.util.List<String> quantity = new ArrayList<>();
        quantity.add("Quantité");
        for (int i = 1; i< 11; i++) {
            quantity.add(""+i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailsItemActivity.this, android.R.layout.simple_list_item_1, quantity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemQuantity.setAdapter(adapter);

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
                        if(jsonLists.length() > 0) {
                            for (int i = 0; i < jsonLists.length(); i++) {
                                List l = new List(jsonLists.getJSONObject(i));
                                lists.add(l);
                                spinnerData.add(l.getName());
                            }


                        } else {
                            spinnerData.add("Pas de liste");
                            listChoice.setEnabled(false);

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


        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = itemQuantity.getSelectedItemPosition();
                if(quantity > 0) {
                    List selectedList = lists.get(listChoice.getSelectedItemPosition());
                    Map<String, Object> body = new HashMap<String, Object>();
                    body.put("quantity", quantity);

                    AsyncWebServices.put("/list/" + selectedList.getUid() + "/item/" + item.getUid(), body, new INetworkListener() {
                        @Override
                        public void onComplete(NetworkResponse response) {
                            if(response != null) {
                                Toast.makeText(DetailsItemActivity.this, "Ajout effectué", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(DetailsItemActivity.this, "Erreur lors de l'ajout", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, headers);
                }else {
                    Toast.makeText(DetailsItemActivity.this, "Vous devez sélectionner une quantité", Toast.LENGTH_LONG).show();
                }
            }
        });
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
