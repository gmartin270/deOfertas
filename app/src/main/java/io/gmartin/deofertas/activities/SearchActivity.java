package io.gmartin.deofertas.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.SearchController;
import io.gmartin.deofertas.models.Search;
import io.gmartin.deofertas.models.Store;
import io.gmartin.deofertas.widget.MultiSelectionSpinner;

public class SearchActivity extends NavigationActivity implements SearchController.SearchControllerListener{

    public final static String EXTRA_SEARCH = "io.gmartin.deofertas.activities.SEARCH";
    private SearchView mSearchEV;
    private EditText mPriceFrom;
    private EditText mPriceTo;
    private Button mSearchBtn;
    private Button mAdvSearchBtn;
    private Search mSearch;
    private MultiSelectionSpinner mStoreSpinner;
    private LinearLayout mAdvancedSearchLayout;
    private boolean mIsAdvancedSearch = false;
    private List<Store> mStoreList;
    private SearchController mSearchController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchController = new SearchController(this);
        mSearchController.fetchStores();

        setContentView(R.layout.activity_search);
        mDrawerLayout = findViewById(R.id.drawer_search_layout);

        initUI();

        mSearchEV = findViewById(R.id.search_box);
        mSearchEV.setSubmitButtonEnabled(true);
        mPriceFrom = findViewById(R.id.edit_price_from);
        mPriceTo = findViewById(R.id.edit_price_to);
        mSearchBtn = findViewById(R.id.button_search);
        mAdvSearchBtn = findViewById(R.id.button_advanced_search);
        mAdvancedSearchLayout = findViewById(R.id.advanced_search_layout);
        mStoreSpinner = findViewById(R.id.store_spinner);

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
        List<Store> storesSelected;
        List<Integer> storesIndexes;
        mSearch = new Search();

        mSearch.setText(mSearchEV.getQuery().toString());

        if (mPriceFrom.getText().toString().length() > 0) {
            priceFrom = Double.valueOf(mPriceFrom.getText().toString());
            mSearch.setPriceFrom(priceFrom);
        }

        if (mPriceTo.getText().toString().length() > 0) {
            priceTo = Double.valueOf(mPriceTo.getText().toString());
            mSearch.setPriceTo(priceTo);
        }

        storesIndexes = mStoreSpinner.getSelectedIndexes();

        if (storesIndexes != null && storesIndexes.size() > 0) {
            storesSelected = new ArrayList<>();

            for (Integer index: storesIndexes) {
                storesSelected.add(mStoreList.get(index));
            }

            mSearch.setStores(storesSelected);
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

    @Override
    public void onDataReceived(Object data) {
        if (data != null) {
            mStoreList = (List<Store>) data;
            mStoreSpinner.setItems(mSearchController.storeToString(mStoreList));
        }
    }
}
