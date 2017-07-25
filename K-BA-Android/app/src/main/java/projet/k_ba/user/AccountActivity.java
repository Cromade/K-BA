package projet.k_ba.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import projet.k_ba.MainActivity;
import projet.k_ba.R;
import projet.k_ba.models.User;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;
import projet.k_ba.utils.InputFieldTest;

public class AccountActivity extends AppCompatActivity {
    private EditText firstname_text_edit;
    private EditText lastname_text_edit;
    private EditText email_text_edit;
    private EditText pseudo_text_edit;
    private EditText pwd_text_edit;
    private EditText birthdate_text_edit;
    private EditText address_text_edit;
    private EditText zipcode_text_edit;
    private EditText city_text_edit;
    private Button back_button;
    private Button saveButton;
    private String token;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        this.token = this.getIntent().getStringExtra("token");
        this.user = this.getIntent().getParcelableExtra("user");
        findViewsById();

        firstname_text_edit.setText(user.getFirstname());
        lastname_text_edit.setText(user.getLastname());
        email_text_edit.setText(user.getEmail());
        pseudo_text_edit.setText(user.getPseudo());
        pwd_text_edit.setText(user.getPassword());
        birthdate_text_edit.setText(user.getBirthdate());
        address_text_edit.setText(user.getAddress().getAddress());
        zipcode_text_edit.setText(user.getAddress().getZipcode());
        city_text_edit.setText(user.getAddress().getCity());


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email_text_edit.getText().toString().equals("")) {
                    if (!pwd_text_edit.getText().equals("")) {
                        subscribeButton();

                    } else {
                        pwd_text_edit.setError(getApplicationContext().getString(R.string.required));
                    }
                } else {
                    email_text_edit.setError(getApplicationContext().getString(R.string.required));
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent MainActivity = new Intent(AccountActivity.this, projet.k_ba.HomeActivity.class);
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
        email_text_edit = (EditText) findViewById(R.id.email_text_edit);
        pseudo_text_edit = (EditText) findViewById(R.id.pseudo_text_edit);
        pwd_text_edit = (EditText) findViewById(R.id.pwd_text_edit);
        birthdate_text_edit = (EditText) findViewById(R.id.birthdate_text_edit);
        address_text_edit = (EditText) findViewById(R.id.address_text_edit);
        zipcode_text_edit = (EditText) findViewById(R.id.zipcode_text_edit);
        city_text_edit = (EditText) findViewById(R.id.city_text_edit);
        saveButton = (Button) findViewById(R.id.validate_inscription_button);
        back_button = (Button) findViewById(R.id.back_button);
    }


    private void subscribeButton() {
        String firstname = firstname_text_edit.getText().toString();
        String lastname = lastname_text_edit.getText().toString();
        String email = email_text_edit.getText().toString();
        String pseudo = pseudo_text_edit.getText().toString();
        String pwd = pwd_text_edit.getText().toString();
        String birthdate = birthdate_text_edit.getText().toString();
        String address = address_text_edit.getText().toString();
        String zipcode = zipcode_text_edit.getText().toString();
        String city = city_text_edit.getText().toString();

        String message = InputFieldTest.isInputValid(lastname, firstname, pseudo, email, pwd, birthdate, address, zipcode, city);
        if(message.length() > 0) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("firstname", firstname_text_edit.getText().toString());
        params.put("lastname", lastname_text_edit.getText().toString());
        params.put("pseudo", pseudo_text_edit.getText().toString());
        params.put("email", email_text_edit.getText().toString());
        params.put("password", pwd_text_edit.getText().toString());
        params.put("birthdate", birthdate_text_edit.getText().toString());
        params.put("address", address_text_edit.getText().toString());
        params.put("zipcode", zipcode_text_edit.getText().toString());
        params.put("city", city_text_edit.getText().toString());

        AsyncWebServices.post("/auth/subscribe", params, new INetworkListener() {
            @Override
            public void onComplete(NetworkResponse response) {
                Log.d("NETWORK", response.getBody());
                if(response.toString() != null) {
                    try {
                        JSONObject object = new JSONObject(response.getBody());
                        if((object.getString("uid")) != null) {
                            Toast popup = Toast.makeText(getApplicationContext(), R.string.save, Toast.LENGTH_LONG);
                            popup.show();
                            finish();
                            return;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Toast popup = Toast.makeText(getApplicationContext(), R.string.fail, Toast.LENGTH_LONG);
                popup.show();

            }
        });
    }
}

