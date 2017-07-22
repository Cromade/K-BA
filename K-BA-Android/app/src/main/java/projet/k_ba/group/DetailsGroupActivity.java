package projet.k_ba.group;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import projet.k_ba.R;
import projet.k_ba.adapter.UserAdapter;
import projet.k_ba.models.Group;
import projet.k_ba.models.User;
import projet.k_ba.user.AddUserActivity;
import projet.k_ba.user.DetailsUserActivity;
import projet.k_ba.user.UserActivity;

public class DetailsGroupActivity extends AppCompatActivity {
    private ListView userListView;
    private String token;
    private Group group;
    private Button addMemberButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_group);
        findViewsById();
        token = this.getIntent().getStringExtra("token");
        group = this.getIntent().getParcelableExtra("group");

        this.userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = (User) userListView.getAdapter().getItem(position);
                Intent detailsGroupActivity = new Intent(DetailsGroupActivity.this, DetailsUserActivity.class);
                detailsGroupActivity.putExtra("group", selectedUser);
                detailsGroupActivity.putExtra("token", DetailsGroupActivity.this.token);
                startActivity(detailsGroupActivity);
            }
        });

        UserAdapter userAdapter = new UserAdapter(this, this.group.getMembers());
        userListView.setAdapter(userAdapter);
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addGroupActivity = new Intent(DetailsGroupActivity.this, AddUserActivity.class);
                addGroupActivity.putExtra("token", DetailsGroupActivity.this.token);
                startActivity(addGroupActivity);

            }
        });

    }
    public void findViewsById() {
        userListView = (ListView) findViewById(R.id.user_list_view);
        addMemberButton = (Button) findViewById(R.id.add_member_button);
    }
}
