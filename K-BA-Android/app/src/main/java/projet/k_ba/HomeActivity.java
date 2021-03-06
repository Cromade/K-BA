package projet.k_ba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import projet.k_ba.adapter.PreferenceAdapter;
import projet.k_ba.group.GroupActivity;
import projet.k_ba.item.ItemActivity;
import projet.k_ba.list.ListActivity;
import projet.k_ba.models.User;
import projet.k_ba.preference.PreferenceActivity;
import projet.k_ba.user.AccountActivity;

public class HomeActivity extends AppCompatActivity {


    private Button gestionGroupButton;
    private Button productListButton;
    private Button gestionPreference;
    private Button gestionAccount;
    private Button gestionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewsById();

       gestionGroupButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent Group = new Intent(HomeActivity.this, GroupActivity.class);
               Group.putExtra("token",HomeActivity.this.getIntent().getStringExtra("token"));
               Group.putExtra("user", HomeActivity.this.getIntent().getParcelableExtra("user"));
               startActivity(Group);
           }
       });

       productListButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent Item = new Intent(HomeActivity.this, ItemActivity.class);
               Item.putExtra("token",HomeActivity.this.getIntent().getStringExtra("token"));
               Item.putExtra("user", HomeActivity.this.getIntent().getParcelableExtra("user"));
               startActivity(Item);
           }
       });
        gestionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent List = new Intent(HomeActivity.this, ListActivity.class);
                List.putExtra("token",HomeActivity.this.getIntent().getStringExtra("token"));
                List.putExtra("user", HomeActivity.this.getIntent().getParcelableExtra("user"));
                startActivity(List);
            }
        });

        gestionPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Preference = new Intent(HomeActivity.this, PreferenceActivity.class);
                Preference.putExtra("token",HomeActivity.this.getIntent().getStringExtra("token"));
                Preference.putExtra("user", HomeActivity.this.getIntent().getParcelableExtra("user"));
                startActivity(Preference);
            }
        });
        gestionAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Account = new Intent(HomeActivity.this, AccountActivity.class);
                Account.putExtra("token",HomeActivity.this.getIntent().getStringExtra("token"));
                Account.putExtra("user", HomeActivity.this.getIntent().getParcelableExtra("user"));
                startActivity(Account);
            }
        });
    }

    public void findViewsById() {
        gestionGroupButton = (Button) findViewById(R.id.gestion_group);
        productListButton = (Button)findViewById(R.id.list_produits);
        gestionAccount = (Button)findViewById(R.id.gestion_compte);
        gestionPreference = (Button)findViewById(R.id.gestion_preference);
        gestionList = (Button)findViewById(R.id.gestion_list);

    }


}
