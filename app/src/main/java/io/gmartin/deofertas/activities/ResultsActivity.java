package io.gmartin.deofertas.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.ResultController;
import io.gmartin.deofertas.fragments.DetailFragment;
import io.gmartin.deofertas.fragments.ListFragment;
import io.gmartin.deofertas.fragments.SearchFragment;
import io.gmartin.deofertas.models.Offer;
import io.gmartin.deofertas.models.Search;

public class ResultsActivity extends Activity
                            implements ListFragment.OnOffersListInteractionListener,
                                       DetailFragment.OnDetailInteractionListener,
                                       ResultController.OfferControllerListener{

    private FragmentManager mManager;
    private ListFragment mList = new ListFragment();
    private DetailFragment mDetail = new DetailFragment();
    private Boolean mIsPort = null;
    private List<Offer> mOffers;
    private ResultController mController;
    private int mReturn;
    private Intent mIntent;

    public List<Offer> getOfferList(){
        return mOffers;
    }

    public Boolean getIsPort() {
        return mIsPort;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mIntent = getIntent();
        Search search = (Search) mIntent.getSerializableExtra(SearchFragment.EXTRA_SEARCH);

        View container = findViewById(R.id.containerResult);
        mIsPort = container!=null;
        mManager = getFragmentManager();

        FragmentTransaction transaction = mManager.beginTransaction();

        if(mOffers == null) {
            mController = new ResultController(this);
            mController.fetchOffers(search);
        }else{
            mList.setOfferList(mOffers);
        }

        if(mIsPort) {
            transaction.replace(R.id.containerResult, mList);
            transaction.commit();
        } else {
            transaction.replace(R.id.listContainer, mList);
            transaction.replace(R.id.detailContainer, mDetail);
            transaction.commit();
        }
    }

    @Override
    public void onSelectedOffer(Offer offer) {
        if (mIsPort) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.container, mDetail);
            transaction.commit();
        }

        mDetail.setOffer(offer);
    }

    @Override
    public void onCloseButtonClick() {
        mList.setOfferList(mOffers);

        if(mIsPort) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.container, mList);
            transaction.commit();
        }
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
}
