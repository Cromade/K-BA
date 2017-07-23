package projet.k_ba.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import projet.k_ba.R;
import projet.k_ba.adapter.ItemAdapter;
import projet.k_ba.adapter.UserAdapter;
import projet.k_ba.group.AddMemberToGroupActivity;
import projet.k_ba.models.Group;
import projet.k_ba.models.List;

public class DetailsListActivity extends AppCompatActivity {
    private ListView itemListView;
    private String token;
    private List list;
    private Button addMemberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);


        /*this.userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = (User) userListView.getAdapter().getItem(position);
                Intent detailsGroupActivity = new Intent(DetailsGroupActivity.this, DetailsUserActivity.class);
                detailsGroupActivity.putExtra("group", selectedUser);
                detailsGroupActivity.putExtra("token", DetailsGroupActivity.this.token);
                startActivity(detailsGroupActivity);
            }
        });*/

            ItemAdapter itemAdapter = new ItemAdapter(this, this.list.getItems());
            itemListView.setAdapter(itemAdapter);

        }
    public void findViewsById() {
        userListView = (ListView) findViewById(R.id.user_list_view);
        addMemberButton = (Button) findViewById(R.id.add_member_button);
    }
    }
}
