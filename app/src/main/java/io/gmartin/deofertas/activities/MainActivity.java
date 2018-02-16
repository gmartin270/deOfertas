package io.gmartin.deofertas.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.ResultController;
import io.gmartin.deofertas.fragments.SearchFragment;
import io.gmartin.deofertas.fragments.SettingsFragment;
import io.gmartin.deofertas.fragments.SuggestedFragment;
import io.gmartin.deofertas.models.OfferImage;
import io.gmartin.deofertas.models.Search;

public class MainActivity extends NavigationActivity
        implements SearchFragment.OnSearchInteractionListener,
                   ResultController.ResultControllerListener,
                   SuggestedFragment.OnSuggestedInteractionListener {

    public static final String SEARCH_INTENT_EXTRA = "io.gmartin.deofertas.activities.search_intent_extra";
    private FragmentManager mManager;
    private SearchFragment mSearchFragment = new SearchFragment();
    private SettingsFragment mSettingsFragment = new SettingsFragment();
    private SuggestedFragment mSuggestedFragment = new SuggestedFragment();
    private int mContainer;
    private List<OfferImage> mOfferImages;
    private ResultController mResultController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = MAIN_ACTIVITY;
        initUI();

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
            fragment = mSearchFragment;
        } else if (mAction.equals(SETTINGS_ACTION)) {
            fragment = mSettingsFragment;
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

    @Override
    public void onSuggestedButtonClick() {
        mManager = getFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.replace(mContainer, mSuggestedFragment);
        transaction.commit();
    }

    @Override
    public void onDataRequested() {
        if(mOfferImages == null) {
            //mList.setProgressBarVisibility(View.VISIBLE);
            mResultController = new ResultController(this);

            //TODO: Implements Correctly suggests
            mResultController.fetchOfferImages(new Long(1));

        }else{
            mSuggestedFragment.setOfferImages(mOfferImages);
        }
    }

    @Override
    public void onImageDataReceived(List<OfferImage> offerImages) {
        mOfferImages = offerImages;

        if (mOfferImages != null && mOfferImages.size() > 0) {
            mSuggestedFragment.setOfferImages(mOfferImages);

            //TODO: Implements progress bar for images.
            //mList.setProgressBarVisibility(View.GONE);
        }
    }
}
