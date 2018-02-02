package io.gmartin.deofertas.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.models.Search;

public class SearchActivity extends Activity {

    public final static String EXTRA_SEARCH = "io.gmartin.deofertas.activities.SEARCH";
    private SearchView mSearchEV;
    private EditText mPriceFrom;
    private EditText mPriceTo;
    private Button mSearchBtn;
    private Button mAdvSearchBtn;
    private Search mSearch;
    private LinearLayout mAdvancedSearchLayout;
    private boolean mIsAdvancedSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchEV = findViewById(R.id.search_box);
        mSearchEV.setSubmitButtonEnabled(true);
        mPriceFrom = findViewById(R.id.edit_price_from);
        mPriceTo = findViewById(R.id.edit_price_to);
        mSearchBtn = findViewById(R.id.button_search);
        mAdvSearchBtn = findViewById(R.id.button_advanced_search);
        mAdvancedSearchLayout = findViewById(R.id.advanced_search_layout);

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
        Double priceFrom = null;
        Double priceTo = null;
        mSearch = new Search();

        mSearch.setText(mSearchEV.getQuery().toString());

        if (mPriceFrom.getText().toString().length() > 0) {
            priceFrom = new Double(mPriceFrom.getText().toString());
            mSearch.setPriceFrom(priceFrom);
        }

        if (mPriceTo.getText().toString().length() > 0) {
            priceTo = new Double(mPriceTo.getText().toString());
            mSearch.setPriceTo(priceTo);
        }

        if (priceFrom != null && priceTo != null && priceFrom > priceTo) {
            Toast.makeText(this, getResources().getString(R.string.price_from_greater_to), Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra(EXTRA_SEARCH, mSearch);
            startActivity(intent);
        }
    }

    private void advancedSearchOffers() {
        if (mIsAdvancedSearch) {
            mAdvSearchBtn.setText(getResources().getString(R.string.button_advanced_search_text));
            mAdvancedSearchLayout.setVisibility(View.GONE);
            mPriceFrom.setText(null);
            mPriceTo.setText(null);
        } else {
            mAdvSearchBtn.setText(getResources().getString(R.string.button_basic_search_text));
            mAdvancedSearchLayout.setVisibility(View.VISIBLE);
        }

        mIsAdvancedSearch = !mIsAdvancedSearch;
    }
}
