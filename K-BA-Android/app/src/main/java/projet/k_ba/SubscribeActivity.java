package projet.k_ba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;

public class SubscribeActivity extends AppCompatActivity {

    private EditText firstname_text_edit;
    private EditText lastname_text_edit;
    private EditText pseudo_text_edit;
    private EditText pwd_text_edit;
    private EditText birthdate_text_edit;
    private EditText address_text_edit;
    private EditText zipcode_text_edit;
    private EditText city_text_edit;
    private Button back_button;
    private Button validate_inscription_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);


        findViewsById();

        validate_inscription_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pseudo_text_edit.getText().toString().equals("")) {
                    if (!pwd_text_edit.getText().equals("")) {
                        subscribeButton();
                    } else {
                        pwd_text_edit.setError(getApplicationContext().getString(R.string.required));
                    }
                } else {
                    pseudo_text_edit.setError(getApplicationContext().getString(R.string.required));
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent MainActivity = new Intent(SubscribeActivity.this, MainActivity.class);
                    startActivity(MainActivity); // nouvelle page
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void findViewsById() {
        firstname_text_edit = (EditText)findViewById(R.id.firstname_text_edit);
        lastname_text_edit = (EditText) findViewById(R.id.lastname_text_edit);
        pseudo_text_edit = (EditText) findViewById(R.id.pseudo_text_edit);
        pwd_text_edit = (EditText) findViewById(R.id.pwd_text_edit);
        birthdate_text_edit = (EditText) findViewById(R.id.birthdate_text_edit);
        address_text_edit = (EditText) findViewById(R.id.address_text_edit);
        zipcode_text_edit = (EditText) findViewById(R.id.zipcode_text_edit);
        city_text_edit = (EditText) findViewById(R.id.city_text_edit);
        validate_inscription_button = (Button) findViewById(R.id.validate_inscription_button);
        back_button = (Button) findViewById(R.id.back_button);
    }


    private void subscribeButton() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("firstname", firstname_text_edit.getText().toString());
        params.put("lastname", lastname_text_edit.getText().toString());
        params.put("pseudo", pseudo_text_edit.getText().toString());
        params.put("email", "test@test.com");
        params.put("password", pwd_text_edit.getText().toString());
        params.put("birthdate", birthdate_text_edit.getText().toString());
        params.put("address", address_text_edit.getText().toString());
        params.put("zipcode", zipcode_text_edit.getText().toString());
        params.put("city", city_text_edit.getText().toString());

        AsyncWebServices.post("/auth/subscribe", params, new INetworkListener() {
            @Override
            public void onComplete(String response) {
                Log.d("NETWORK", response);
            }
        });
    }
}

