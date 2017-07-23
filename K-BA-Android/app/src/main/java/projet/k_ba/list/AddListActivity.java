package projet.k_ba.list;

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

public class AddListActivity extends AppCompatActivity {

    private Button validateNewList;
    private EditText nameList;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

            findViewsById();
            token = this.getIntent().getStringExtra("token");

            validateNewList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer "+ token);
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("name", nameList.getText().toString());
                    AsyncWebServices.post("/list", params, new INetworkListener() {
                        @Override
                        public void onComplete(NetworkResponse response) {
                            if (response != null) {
                                Toast.makeText(AddListActivity.this, "Création de liste effectuée", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(AddListActivity.this, "Erreur lors de la création de la liste", Toast.LENGTH_LONG).show();
                            }
                        }
                    },headers);
                }
            });
        }


    public void findViewsById() {
        validateNewList = (Button) findViewById(R.id.validate_new_list);
        nameList = (EditText) findViewById(R.id.edit_text_new_list);
    }
}
