package io.gmartin.deofertas.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.fragments.DetailFragment;
import io.gmartin.deofertas.fragments.ListFragment;
import io.gmartin.deofertas.models.Offer;
import io.gmartin.deofertas.models.Search;

public class ResultsActivity extends Activity
                            implements ListFragment.OnOffersListInteractionListener,
                                       DetailFragment.OnDetailInteractionListener {

    private FragmentManager mManager;
    private ListFragment mList = new ListFragment();
    private DetailFragment mDetail = new DetailFragment();
    private Boolean mIsPort = null;

    public Boolean getIsPort() {
        return mIsPort;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intentSearch = getIntent();
        Search search = (Search) intentSearch.getSerializableExtra(SearchActivity.EXTRA_SEARCH);

        View container = findViewById(R.id.container);
        mIsPort = container!=null;
        mManager = getFragmentManager();

        FragmentTransaction transaction = mManager.beginTransaction();

        if(mIsPort) {
            transaction.replace(R.id.container, mList);
            transaction.commit();
        } else {
            transaction.replace(R.id.listContainer, mList);
            transaction.replace(R.id.detailContainer, mDetail);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSelectedOffer(Offer offer) {
        if (mIsPort) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.container, mDetail);
            transaction.commit();
        }

        if (offer != null) {
            mDetail.setOffer(offer);
        }
    }

    @Override
    public void onCloseButtonClick() {
        if(mIsPort) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.container, mList);
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
