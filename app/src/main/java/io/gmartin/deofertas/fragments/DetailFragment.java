package io.gmartin.deofertas.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.activities.ResultsActivity;
import io.gmartin.deofertas.models.Offer;

public class DetailFragment extends Fragment {

    private static View mRoot;
    private Offer mOffer = null;
    private OnDetailInteractionListener mListener;
    private Button mCloseBtn;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mOffer != null) {
            if (mRoot != null) {
                updateOffer();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_detail, container, false);
        }

        mCloseBtn = mRoot.findViewById(R.id.btnClose);

        if (((ResultsActivity)getActivity()).getIsPort()) {
            mCloseBtn.setVisibility(View.VISIBLE);
        } else {
            mCloseBtn.setVisibility(View.GONE);
        }

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mListener.onCloseButtonClick();
                }catch (Exception e){

                }
            }
        });

        return mRoot;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailInteractionListener) {
            mListener = (OnDetailInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailInteractionListener");
        }
    }

    public void setOffer(Offer offer) {
        mOffer = offer;

        if (mRoot != null) {
            updateOffer();
        }
    }

    private void updateOffer(){
        TextView desc = mRoot.findViewById(R.id.txtDesc);
        TextView store = mRoot.findViewById(R.id.txtStore);
        TextView price = mRoot.findViewById(R.id.txtPrice);

        desc.setText(mOffer.getDesc());
        store.setText(mOffer.getStoreName());
        price.setText(String.format("%.2f", mOffer.getPrice()));
    }

    public interface OnDetailInteractionListener {
        void onCloseButtonClick();
    }
}