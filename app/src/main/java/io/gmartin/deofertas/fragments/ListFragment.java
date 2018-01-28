package io.gmartin.deofertas.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.adapters.OfferAdapter;
import io.gmartin.deofertas.models.Offer;


public class ListFragment extends Fragment {

    private View mRoot;
    private OnOffersListInteractionListener mListener;
    private Context mContext;
    private ListView mList;
    private OfferAdapter mAdapter;

    public interface OnOffersListInteractionListener {
        void onSelectedOffer(Offer offer);
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        if (mContext instanceof OnOffersListInteractionListener) {
            mListener = (OnOffersListInteractionListener) mContext;
        } else {
            throw new RuntimeException(mContext.toString()
                    + " must implement OnOffersListInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_list, container, false);
        mAdapter = new OfferAdapter(mContext);

        mList = mRoot.findViewById(R.id.listOffers);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mListener.onSelectedOffer((Offer)mAdapter.getItem(position));
                }catch(Exception e){

                }
            }
        });

        return mRoot;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
