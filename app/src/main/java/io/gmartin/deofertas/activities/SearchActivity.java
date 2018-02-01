package io.gmartin.deofertas.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.models.Search;

public class SearchActivity extends Activity {

    public final static String EXTRA_SEARCH = "io.gmartin.deofertas.activities.SEARCH";
    private EditText mSearchEV;
    private Button mSearchBtn;
    private Button mAdvSearchBtn;
    private Search mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchEV = findViewById(R.id.search_box);
        mSearchBtn = findViewById(R.id.button_search);
        mAdvSearchBtn = findViewById(R.id.button_advanced_search);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchOffers();
            }
        });

        mAdvSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advancedSearchOffers();
            }
        });
    }

    private void searchOffers() {
        mSearch = new Search();
        mSearch.setText(((EditText)findViewById(R.id.search_box)).getText().toString());

        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(EXTRA_SEARCH, mSearch);
        startActivity(intent);
    }

    private void advancedSearchOffers() {
        Intent intent = new Intent(this, AdvancedSearchActivity.class);
        startActivity(intent);
    }
}
