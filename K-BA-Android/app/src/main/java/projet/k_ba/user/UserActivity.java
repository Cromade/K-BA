package projet.k_ba.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projet.k_ba.R;
import projet.k_ba.adapter.GroupAdapter;
import projet.k_ba.group.AddGroupActivity;
import projet.k_ba.group.DetailsGroupActivity;
import projet.k_ba.group.GroupActivity;
import projet.k_ba.models.Group;
import projet.k_ba.models.User;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;

public class UserActivity extends AppCompatActivity {
    private ListView userListView;
    private String token;
    private User user;
    private Button addMemberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);this.token = this.getIntent().getStringExtra("token");
        this.user = this.getIntent().getParcelableExtra("user");
        Log.d(" TOKEN = ",token);
        Log.d(" USER = ",user.toString());

        findViewsById();



        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addGroupActivity = new Intent(UserActivity.this, AddUserActivity.class);
                addGroupActivity.putExtra("token", UserActivity.this.token);
                startActivity(addGroupActivity);

            }
        });

    }

    public void findViewsById() {
        userListView = (ListView) findViewById(R.id.user_list_view);
        addMemberButton = (Button) findViewById(R.id.add_member_button);
    }

}
