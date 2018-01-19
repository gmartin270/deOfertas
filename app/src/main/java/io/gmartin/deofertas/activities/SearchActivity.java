package io.gmartin.deofertas.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.gmartin.deofertas.R;

public class SearchActivity extends Activity {

    private EditText mSearchEV;
    private Button mSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchEV = findViewById(R.id.search_box);
        mSearchBtn = findViewById(R.id.button_search);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchOffers();
            }
        });
    }


    private void searchOffers() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }
}
