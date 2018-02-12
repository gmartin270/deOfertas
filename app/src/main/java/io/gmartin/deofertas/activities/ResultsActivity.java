package io.gmartin.deofertas.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.BaseController;
import io.gmartin.deofertas.controllers.ResultController;
import io.gmartin.deofertas.fragments.DetailFragment;
import io.gmartin.deofertas.fragments.ListFragment;
import io.gmartin.deofertas.fragments.SearchFragment;
import io.gmartin.deofertas.models.Offer;
import io.gmartin.deofertas.models.Search;

public class ResultsActivity extends NavigationActivity
                            implements ListFragment.OnOffersListInteractionListener,
                                       DetailFragment.OnDetailInteractionListener,
                                       BaseController.BaseControllerListener{

    private FragmentManager mManager;
    private ListFragment mList = new ListFragment();
    private DetailFragment mDetail = new DetailFragment();
    private List<Offer> mOffers;
    private ResultController mController;

    public List<Offer> getOfferList(){
        return mOffers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_main);
        mActivity = RESULTS_ACTIVITY;
        initUI();

        Intent intent = getIntent();
        Search search = (Search) intent.getSerializableExtra(MainActivity.SEARCH_INTENT_EXTRA);

        if(mOffers == null) {
            mController = new ResultController(this);
            mController.fetchOffers(search);
        }else{
            mList.setOfferList(mOffers);
        }

        mManager = getFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();

        if(getIsPort()) {
            transaction.replace(R.id.container_result, mList);
            transaction.commit();
        } else {
            transaction.replace(R.id.listContainer, mList);
            transaction.replace(R.id.detailContainer, mDetail);
            transaction.commit();
        }
    }

    @Override
    public void onSelectedOffer(Offer offer) {
        if (getIsPort()) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.container_result, mDetail);
            transaction.commit();
        }

        mDetail.setOffer(offer);
    }

    @Override
    public void onCloseButtonClick() {
        mList.setOfferList(mOffers);

        if(getIsPort()) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.container_result, mList);
            transaction.commit();
        }
    }

    @Override
    public void onFavoriteButtonClick(Offer offer) {
        if (offer.isFavorite()) {
            mController.saveFavorite(offer);
        } else {
            mController.removeFavorite(offer.getId());
        }

        Toast.makeText(this, "FAVORITE", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataReceived(Object object) {
        mOffers = (List<Offer>)object;

        if (mOffers != null && mOffers.size() > 0) {
            mList.setOfferList(mOffers);
        } else {
            Toast toast = Toast.makeText(this, getResources().getString(R.string.no_data_result), Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
    }

    @Override
    public void onErrorEvent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
