package projet.k_ba.group;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import projet.k_ba.R;
import projet.k_ba.models.Group;
import projet.k_ba.network.AsyncWebServices;

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
                AsyncWebServices.get("/user?search="+)
            }
        });
    }

    public void findViewsById() {
        searchUserButton = (Button) findViewById(R.id.search_user_button);
        editTextSearchUser = (EditText) findViewById(R.id.edit_text_add_member);
    }

}
