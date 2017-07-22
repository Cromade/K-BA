package projet.k_ba.group;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import projet.k_ba.R;
import projet.k_ba.models.User;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;

public class GroupActivity extends AppCompatActivity {

    private ListView group_list_view;
    private String token;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        this.token = this.getIntent().getStringExtra("token");
        this.user = this.getIntent().getParcelableExtra("user");
        Log.d(" TOKEN = ",token);
        Log.d(" USER = ",user.toString());

        findViewsById();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer "+ token);
       AsyncWebServices.get("/group?user_uid=" + user.getUid(), new INetworkListener() {
           @Override
           public void onComplete(NetworkResponse response) {
               Log.d("RES ", response.getBody());
           }
       },headers);

    }

    public void findViewsById() {
        group_list_view = (ListView) findViewById(R.id.group_list_view);

    }



}
