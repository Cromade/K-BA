package projet.k_ba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import projet.k_ba.group.GroupActivity;
import projet.k_ba.models.User;

public class HomeActivity extends AppCompatActivity {


    private Button gestionGroupButton;

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
               finish();
           }
       });


    }

    public void findViewsById() {
        gestionGroupButton = (Button) findViewById(R.id.gestion_group);

    }


}
