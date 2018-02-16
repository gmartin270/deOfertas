package io.gmartin.deofertas.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.activities.ResultsActivity;
import io.gmartin.deofertas.adapters.OfferAdapter;
import io.gmartin.deofertas.models.Offer;


public class ListFragment extends Fragment implements OfferAdapter.ItemClickListener {

    private View mRoot;
    private OnOffersListInteractionListener mListener;
    private Context mContext;
    private ListViewCompat mList;
    private OfferAdapter mAdapter;
    private ProgressBar mProgressBar;

    public interface OnOffersListInteractionListener {
        void onSelectedOffer(Offer offer);

        void onDataRequested();
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mContext instanceof OnOffersListInteractionListener) {
            mListener = (OnOffersListInteractionListener) mContext;
        }

        mRoot = inflater.inflate(R.layout.fragment_list, container, false);
        mProgressBar = mRoot.findViewById(R.id.progressBar);

        /*RecyclerView recyclerView = getActivity().findViewById(R.id.listOffers);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        if (mAdapter == null) {
            mAdapter = new OfferAdapter(mContext);
        }

        recyclerView.setAdapter(mAdapter);*/


        /*mList = mRoot.findViewById(R.id.listOffers);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mListener.onSelectedOffer((Offer)mAdapter.getItem(position));
                }catch(Exception e){

                }
            }
        });*/



        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mAdapter.updateList(((ResultsActivity)mContext).getOfferList());
        mListener.onDataRequested();

        RecyclerView recyclerView = getActivity().findViewById(R.id.listOffers);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        if (mAdapter == null) {
            mAdapter = new OfferAdapter(mContext);
        }

        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mAdapter.updateList(((ResultsActivity)mContext).getOfferList());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.updateList(((ResultsActivity)mContext).getOfferList());
    }

    public void setOfferList(List<Offer> offers) {
        if (mAdapter == null) {
            mAdapter = new OfferAdapter(mContext);
        }
        mAdapter.updateList(offers);

    }

    public void setProgressBarVisibility(int visibility) {
        mProgressBar.setVisibility(visibility);
    }

    @Override
    public void onClick(View view, int position) {
        mListener.onSelectedOffer((Offer)mAdapter.getItem(position));
    }
}
