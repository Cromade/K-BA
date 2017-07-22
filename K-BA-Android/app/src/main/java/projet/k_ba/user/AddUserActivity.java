package projet.k_ba.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import projet.k_ba.R;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;

public class AddUserActivity extends AppCompatActivity {
    private Button validateNewGroup;
    private EditText nameGroup;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);


        findViewsById();
        token = this.getIntent().getStringExtra("token");

        validateNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+ token);
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("name", nameGroup.getText().toString());
                AsyncWebServices.post("/group", params, new INetworkListener() {
                    @Override
                    public void onComplete(NetworkResponse response) {
                        if (response != null) {
                            Toast.makeText(AddUserActivity.this, "Création de groupe effectuée", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(AddUserActivity.this, "Erreur lors de la création de groupe", Toast.LENGTH_LONG).show();
                        }
                    }
                },headers);
            }
        });
    }


    public void findViewsById() {
        validateNewGroup = (Button) findViewById(R.id.validate_new_group);
        nameGroup = (EditText) findViewById(R.id.edit_text_new_group);
    }
}
