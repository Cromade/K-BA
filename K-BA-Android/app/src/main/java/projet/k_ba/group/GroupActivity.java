package projet.k_ba.group;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projet.k_ba.MainActivity;
import projet.k_ba.R;
import projet.k_ba.adapter.GroupAdapter;
import projet.k_ba.models.Group;
import projet.k_ba.models.User;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;

public class GroupActivity extends AppCompatActivity {

    private ListView groupListView;
    private String token;
    private User user;
    private Button addGroupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        this.token = this.getIntent().getStringExtra("token");
        this.user = this.getIntent().getParcelableExtra("user");
        Log.d(" TOKEN = ",token);
        Log.d(" USER = ",user.toString());

        findViewsById();
        this.groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group selectedGroup = (Group) groupListView.getAdapter().getItem(position);
                Intent detailsGroupActivity = new Intent(GroupActivity.this, DetailsGroupActivity.class);
                detailsGroupActivity.putExtra("group", selectedGroup);
                detailsGroupActivity.putExtra("token", GroupActivity.this.token);
                startActivity(detailsGroupActivity);
            }
        });

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer "+ token);
       AsyncWebServices.get("/group?user_uid=" + user.getUid(), new INetworkListener() {
           @Override
           public void onComplete(NetworkResponse response) {
               if (response != null) {
                   try {
                       JSONArray jsonGroups = new JSONArray(response.getBody());
                       ArrayList<Group> groups = new ArrayList<Group>();
                       for(int i = 0; i < jsonGroups.length(); i++) {
                           groups.add(new Group(jsonGroups.getJSONObject(i)));
                       }
                       GroupAdapter groupAdapter = new GroupAdapter(GroupActivity.this, groups);
                       GroupActivity.this.groupListView.setAdapter(groupAdapter);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

               }
           }
       },headers);

        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addGroupActivity = new Intent(GroupActivity.this, AddGroupActivity.class);
                addGroupActivity.putExtra("token", GroupActivity.this.token);
                startActivity(addGroupActivity);

            }
        });

    }

    public void findViewsById() {
        groupListView = (ListView) findViewById(R.id.group_list_view);
        addGroupButton = (Button) findViewById(R.id.add_group_button);
    }



}
