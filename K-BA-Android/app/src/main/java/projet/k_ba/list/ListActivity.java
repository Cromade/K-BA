package projet.k_ba.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import projet.k_ba.R;
import projet.k_ba.adapter.GroupAdapter;
import projet.k_ba.adapter.ListAdapter;
import projet.k_ba.group.DetailsGroupActivity;
import projet.k_ba.models.Group;
import projet.k_ba.models.List;
import projet.k_ba.models.User;
import projet.k_ba.network.AsyncWebServices;
import projet.k_ba.network.INetworkListener;
import projet.k_ba.network.NetworkResponse;

public class ListActivity extends AppCompatActivity {
    private TextView favTextView;
    private ListView listLists;
    private Button addList;
    private String token;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.token = this.getIntent().getStringExtra("token");
        this.user = this.getIntent().getParcelableExtra("user");
        findViewsById();

        this.listLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List selectedList = (List) listLists.getAdapter().getItem(position);
                Intent detailsListActivity = new Intent(ListActivity.this, DetailsListActivity.class);
                detailsListActivity.putExtra("list", selectedList);
                detailsListActivity.putExtra("token", ListActivity.this.token);
                startActivity(detailsListActivity);
            }
        });

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer "+ token);
        AsyncWebServices.get("/list?user_uid=" + user.getUid(), new INetworkListener() {
            @Override
            public void onComplete(NetworkResponse response) {
                if (response != null) {
                    try {
                        JSONArray jsonLists = new JSONArray(response.getBody());
                        ArrayList<List> lists = new ArrayList<List>();
                        for(int i = 0; i < jsonLists.length(); i++) {
                            lists.add(new List(jsonLists.getJSONObject(i)));
                        }
                        ListAdapter listAdapter = new ListAdapter(ListActivity.this, lists);
                        ListActivity.this.listLists.setAdapter(listAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        },headers);

        favTextView.
        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(ListActivity.this, AddListActivity.class);
                listActivity.putExtra("token", ListActivity.this.token);
                startActivity(listActivity);

            }
        });
    }

    public void findViewsById(){
        favTextView = (TextView) findViewById(R.id.fav_list_name);
        listLists = (ListView) findViewById(R.id.list_list_view);
        addList = (Button) findViewById(R.id.add_list_button);

    }
}
