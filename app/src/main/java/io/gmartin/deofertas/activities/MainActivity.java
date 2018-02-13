package io.gmartin.deofertas.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import io.gmartin.deofertas.R;
import io.gmartin.deofertas.fragments.SearchFragment;
import io.gmartin.deofertas.fragments.SettingsFragment;
import io.gmartin.deofertas.models.Search;

public class MainActivity extends NavigationActivity
        implements SearchFragment.OnSearchInteractionListener {

    public static final String SEARCH_INTENT_EXTRA = "io.gmartin.deofertas.activities.search_intent_extra";
    private FragmentManager mManager;
    private SearchFragment mSearch = new SearchFragment();
    private SettingsFragment mSettings = new SettingsFragment();
    private int mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = MAIN_ACTIVITY;
        initUI();

        String title = "";

        if (getIsPort()) {
            mContainer = R.id.container;
        } else {
            mContainer = R.id.container_land;
        }

        Intent intent = getIntent();
        if (intent != null) {
            mAction = intent.getStringExtra(NAVIGATION_INTENT_EXTRA);
        }

        Fragment fragment = null;
        mManager = getFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();

        if (mAction == null || mAction.equals(SEARCH_ACTION)) {
            mAction = SEARCH_ACTION;
            fragment = mSearch;
        } else if (mAction.equals(SETTINGS_ACTION)) {
            fragment = mSettings;
            getSupportActionBar().setTitle(R.string.menu_nav_settings);
        }

        transaction.replace(mContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onSearchButtonClick(Search search) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(SEARCH_INTENT_EXTRA, search);
        intent.putExtra(NAVIGATION_INTENT_EXTRA, RESULTS_ACTION);
        startActivity(intent);
    }
}
