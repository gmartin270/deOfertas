package io.gmartin.deofertas.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.ResultController;
import io.gmartin.deofertas.models.Offer;
import io.gmartin.deofertas.models.Search;

public class ResultsFragment extends Fragment
        implements ListFragment.OnOffersListInteractionListener,
        DetailFragment.OnDetailInteractionListener,
        ResultController.OfferControllerListener{

    private FragmentManager mManager;
    private ListFragment mList = new ListFragment();
    private DetailFragment mDetail = new DetailFragment();
    private Boolean mIsPort = null;
    private List<Offer> mOffers;
    private ResultController mController;
    private Context mContext;
    private View mRoot;
    private Search mSearch;

    public ResultsFragment(){}

    public List<Offer> getOfferList(){
        return mOffers;
    }

    public Boolean getIsPort() {
        return mIsPort;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(mOffers == null) {
            //mController = new ResultController(this.getActivity(), this);
            mController.fetchOffers(mSearch);
        }else{
            mList.setOfferList(mOffers);
        }
    }

    public void getData(Search search){
        mSearch = search;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.result_main, container, false);
        return mRoot;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        /*View container = this.getActivity().findViewById(R.id.container);
        mIsPort = container!=null;*/

        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mIsPort = true;
        } else {
            mIsPort = false;
        }


        //mIsPort = ((MainActivity)this.getActivity()).getIsPort();
        mManager = getChildFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();

        if(mIsPort) {
            transaction.replace(R.id.containerResult, mList).commit();
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
            transaction.replace(R.id.containerResult, mDetail);
            transaction.commit();
        }

        mDetail.setOffer(offer);
    }

    @Override
    public void onCloseButtonClick() {
        mList.setOfferList(mOffers);

        if(mIsPort) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.containerResult, mList);
            transaction.commit();
        }
    }

    @Override
    public void onDataReceived(Object object) {
        mOffers = (List<Offer>)object;

        if (mOffers != null && mOffers.size() > 0) {
            mList.setOfferList(mOffers);
        } else {
            Toast toast = Toast.makeText(mContext, getResources().getString(R.string.no_data_result), Toast.LENGTH_LONG);
            toast.show();

            this.getActivity().onBackPressed();
        }
    }
}