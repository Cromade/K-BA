package projet.k_ba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import projet.k_ba.models.User;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;

public class MainActivity extends AppCompatActivity {

    private Button inscription_button;
    private Button connexion_button;

    private EditText login_text_edit;
    private EditText password_text_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsById();

        connexion_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!login_text_edit.getText().toString().equals("")) {
                    if (!password_text_edit.getText().equals("")) {
                        connexion();
                    } else {
                        password_text_edit.setError(getApplicationContext().getString(R.string.required));
                    }
                } else {
                    login_text_edit.setError(getApplicationContext().getString(R.string.required));
                }
            }
        });
        inscription_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeButton();
            }
        });
    }



    public void findViewsById() {
        inscription_button = (Button) findViewById(R.id.inscription_button);
        connexion_button = (Button) findViewById(R.id.connexion_button);
        login_text_edit = (EditText) findViewById(R.id.login_text_edit);
        password_text_edit = (EditText) findViewById(R.id.password_text_edit);
    }

    private void connexion() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", login_text_edit.getText().toString());
        params.put("password", password_text_edit.getText().toString());
        AsyncWebServices.post("/auth/login", params, new INetworkListener() {
            @Override
            public void onComplete(NetworkResponse response) {
                if(response != null) {
                   String token =  response.getFirstHeader("Token");
                    if(token != null){
                        try {
                            JSONObject result = new JSONObject(response.getBody());
                            Intent Home = new Intent(MainActivity.this, HomeActivity.class);
                            Home.putExtra("token",token);
                            Home.putExtra("user", new User(result));
                            startActivity(Home);
                            finish();
                        } catch (JSONException e) {
                            Toast popup = Toast.makeText(getApplicationContext(), R.string.fail, Toast.LENGTH_LONG);
                            popup.show();
                            return;
                        }

                    }
                } else {
                    Toast popup = Toast.makeText(getApplicationContext(), R.string.fail, Toast.LENGTH_LONG);
                    popup.show();
                }
            }
        });
    }
    private void subscribeButton() {
        try {
            Intent Subscribe = new Intent(MainActivity.this, SubscribeActivity.class);
            startActivity(Subscribe); // nouvelle page
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





