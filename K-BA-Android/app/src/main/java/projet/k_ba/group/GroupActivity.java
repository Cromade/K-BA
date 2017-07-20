package projet.k_ba.group;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import projet.k_ba.R;
import projet.k_ba.network.AsyncWebServices;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        AsyncWebServices.get("/user/:user_uid/group/");
    }


}
