package io.gmartin.deofertas.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.widget.Toast;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.adapters.SlidingSuggestionAdapter;
import io.gmartin.deofertas.controllers.BaseController;
import io.gmartin.deofertas.controllers.ResultController;
import io.gmartin.deofertas.controllers.SuggestionController;
import io.gmartin.deofertas.fragments.SearchFragment;
import io.gmartin.deofertas.fragments.SettingsFragment;
import io.gmartin.deofertas.fragments.ImagePagerFragment;
import io.gmartin.deofertas.models.OfferImage;
import io.gmartin.deofertas.models.Search;
import io.gmartin.deofertas.models.SuggestedOffer;
import io.gmartin.deofertas.services.SuggestionsService;

public class MainActivity extends NavigationActivity
        implements BaseController.BaseControllerListener,
                   SearchFragment.OnSearchInteractionListener,
                   SuggestionController.SuggestionControllerListener {

    public static final String SEARCH_INTENT_EXTRA = "io.gmartin.deofertas.activities.search_intent_extra";
    private FragmentManager mManager;
    private SearchFragment mSearchFragment = new SearchFragment();
    private SettingsFragment mSettingsFragment = new SettingsFragment();
    private ImagePagerFragment mImagePagerFragment = new ImagePagerFragment();
    private int mContainer;
    private List<SuggestedOffer> mSuggestedOffers;
    private SuggestionsService.APIBinder mBinder;

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (SuggestionsService.APIBinder)service;
            //mBinder.getLastSuggestions();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //mBinder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mIsServiceRun = savedInstanceState.getBoolean(IS_SERVICE_RUN, false);
        }

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
            mIsServiceRun = intent.getBooleanExtra(IS_SERVICE_RUN, false);

            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                handleSearch(intent);
            }
        }

        Fragment fragment = null;
        mManager = getFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();

        if (mAction == null || mAction.equals(HOME_ACTION)) {
            fragment = mImagePagerFragment;
        } else if (mAction.equals(SEARCH_ACTION)) {
            fragment = mSearchFragment;
        } else if (mAction.equals(SETTINGS_ACTION)) {
            fragment = mSettingsFragment;
        }

        transaction.replace(mContainer, fragment);
        transaction.commit();

        initFragment();

        //Launch the background service:
        /*Intent serviceIntent = new Intent(this, SuggestionsService.class);
        startService(serviceIntent);*/

        if (!mIsServiceRun) {
            Intent i = new Intent(this, SuggestionsService.class);
            startService(i);
            bindService(i, mConnection, BIND_AUTO_CREATE);
            mIsServiceRun = true;
        }
    }

    @Override
    public void onSearchButtonClick(Search search) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(SEARCH_INTENT_EXTRA, search);
        intent.putExtra(NAVIGATION_INTENT_EXTRA, RESULTS_ACTION);
        startActivity(intent);
    }

    private void handleSearch(Intent intentReceived) {

        String query = intentReceived.getStringExtra(SearchManager.QUERY);
        Search search = new Search();
        search.setText(query);
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(SEARCH_INTENT_EXTRA, search);
        intent.putExtra(NAVIGATION_INTENT_EXTRA, RESULTS_ACTION);
        startActivity(intent);
    }

    private void getSuggestions() {
        SuggestionController controller = new SuggestionController(this);
        controller.fetchSuggestions();
    }

    @Override
    public void onSuggestionDataReceived(List<SuggestedOffer> suggestedOffers) {
        mSuggestedOffers = suggestedOffers;

        if (mSuggestedOffers != null && mSuggestedOffers.size() > 0) {
            PagerAdapter adapter = new SlidingSuggestionAdapter(this, mSuggestedOffers);
            mImagePagerFragment.setAdapter(adapter);

            //TODO: Implements progress bar for images.
            //mList.setProgressBarVisibility(View.GONE);
        }
    }

    @Override
    public void onErrorEvent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataReceived(Object data) {

    }

    @Override
    public void onLastSuggestionDataReceived(SuggestedOffer suggestedOffer) {

    }

    @Override
    protected void initFragment() {
        if (mAction == null || mAction.equals(HOME_ACTION)) {
            mAction = HOME_ACTION;
            getSuggestions();
        } else if (mAction.equals(SEARCH_ACTION)) {
            mAction = SEARCH_ACTION;
            mToolbar.getMenu().removeItem(R.id.action_search);
        } else if (mAction.equals(SETTINGS_ACTION)) {
            getSupportActionBar().setTitle(R.string.menu_nav_settings);
        }
    }
}