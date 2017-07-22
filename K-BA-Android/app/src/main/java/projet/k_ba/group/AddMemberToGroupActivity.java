package projet.k_ba.group;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projet.k_ba.R;
import projet.k_ba.adapter.GroupAdapter;
import projet.k_ba.adapter.UserAdapter;
import projet.k_ba.models.Group;
import projet.k_ba.models.List;
import projet.k_ba.models.User;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;

public class AddMemberToGroupActivity extends AppCompatActivity {
    private ListView userListView;
    private String token;
    private Group group;
    private EditText editTextSearchUser;
    private Button searchUserButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member_to_group);

        findViewsById();
        token = this.getIntent().getStringExtra("token");
        group = this.getIntent().getParcelableExtra("group");

        searchUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+ token);
                AsyncWebServices.get("/user?search=" + editTextSearchUser.getText().toString(), new INetworkListener() {
                    @Override
                    public void onComplete(NetworkResponse response) {
                        if(response != null) {
                            try {
                                JSONArray jsonUsers = new JSONArray(response.getBody());
                                ArrayList<User> users = new ArrayList<User>();
                                for(int i = 0; i< jsonUsers.length(); i++) {
                                    User user = new User(jsonUsers.getJSONObject(i));
                                    users.add(user);
                                }

                                UserAdapter userAdapter = new UserAdapter(AddMemberToGroupActivity.this, users);
                                AddMemberToGroupActivity.this.userListView.setAdapter(userAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, headers);
            }
        });
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = (User) userListView.getAdapter().getItem(position);
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+ token);
                AsyncWebServices.put("/group/" + group.getUid() + "/user/" + selectedUser.getUid(), null, new INetworkListener() {
                    @Override
                    public void onComplete(NetworkResponse response) {
                        if (response != null) {
                            Toast.makeText(AddMemberToGroupActivity.this, "Utilisateur ajouté au groupe", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(AddMemberToGroupActivity.this, "Erreur lors de l'ajout au groupe", Toast.LENGTH_LONG).show();
                        }
                    }
                }, headers);
            }
        });
    }

    public void findViewsById() {
        searchUserButton = (Button) findViewById(R.id.search_user_button);
        editTextSearchUser = (EditText) findViewById(R.id.edit_text_add_member);
        userListView = (ListView) findViewById(R.id.user_list_view);
    }

}
