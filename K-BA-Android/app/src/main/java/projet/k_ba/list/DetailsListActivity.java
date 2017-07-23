package projet.k_ba.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import projet.k_ba.R;
import projet.k_ba.adapter.ItemAdapter;
import projet.k_ba.adapter.UserAdapter;
import projet.k_ba.group.AddMemberToGroupActivity;
import projet.k_ba.models.Group;
import projet.k_ba.models.List;
import projet.k_ba.network.AsyncWebServices;

public class DetailsListActivity extends AppCompatActivity {
    private ListView itemListView;
    private String token;
    private List list;
    private Button addToPref;
    private TextView listName;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);

        findViewsById();

        this.token = getIntent().getStringExtra("token");
        this.list = getIntent().getParcelableExtra("list");

    }

    private void findViewsById() {
        itemListView =(ListView) findViewById(R.id.item_list_view);
        listName = (TextView) findViewById(R.id.list_name);
        total = (TextView) findViewById(R.id.total_list_res);
        addToPref = (Button) findViewById(R.id.add_pref_button);

    }
}
