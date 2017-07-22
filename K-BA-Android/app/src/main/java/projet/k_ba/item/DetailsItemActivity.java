package projet.k_ba.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import projet.k_ba.R;
import projet.k_ba.models.Item;

public class DetailsItemActivity extends AppCompatActivity {

    private String token;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item);

        this.token = getIntent().getStringExtra("token");
        this.item = getIntent().getParcelableExtra("item");

        Log.d("ITEM", item.toString());
    }
}
