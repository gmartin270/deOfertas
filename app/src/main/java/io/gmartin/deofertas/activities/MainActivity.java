package io.gmartin.deofertas.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import io.gmartin.deofertas.R;
import io.gmartin.deofertas.fragments.SearchFragment;
import io.gmartin.deofertas.models.Search;

public class MainActivity extends NavigationActivity
        implements SearchFragment.OnSearchInteractionListener {

    private static final String SEARCH_STATE = "io.gmartin.deofertas.activities.search_state";
    private static final String SEARCH_INTENT_EXTRA = "io.gmartin.deofertas.activities.search_intent_extra";
    private FragmentManager mManager;
    private SearchFragment mSearch = new SearchFragment();
    private int mContainer;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("state", mState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        if (mIsPort) {
            mContainer = R.id.container;
        } else {
            mContainer = R.id.container_land;
        }

        Fragment fragment = null;
        mManager = getFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();

        if (mState == null || mState.equals(SEARCH_STATE)) {
            mState = SEARCH_STATE;
            fragment = mSearch;
        }

        transaction.replace(mContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onSearchButtonClick(Search search) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(SEARCH_INTENT_EXTRA, search);
        startActivity(intent);
    }

    public Boolean isPort() {
        return mIsPort;
    }
}
