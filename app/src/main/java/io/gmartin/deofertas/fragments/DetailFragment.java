package io.gmartin.deofertas.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.activities.ListActivity;
import io.gmartin.deofertas.models.Offer;

public class DetailFragment extends Fragment {

    private View mRoot;
    private Offer mOffer = null;
    private OnDetailInteractionListener mListener;
    private Button mCloseBtn;
    private ImageButton mFavoriteButton;
    private Context mContext;

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

        if (((ListActivity)mContext).getIsPort()) {
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

        mFavoriteButton = mRoot.findViewById(R.id.favorite_button);

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mOffer.setFavorite(!mOffer.isFavorite());
                    mListener.onFavoriteButtonClick(mOffer);
                }catch (Exception e){

                }
            }
        });

        if (mContext instanceof OnDetailInteractionListener) {
            mListener = (OnDetailInteractionListener) mContext;
        } else {
            throw new RuntimeException(mContext.toString()
                    + " must implement OnDetailInteractionListener");
        }

        return mRoot;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void setOffer(Offer offer) {
        mOffer = offer;

        if (mRoot != null) {
            updateOffer();
        }
    }

    private void updateOffer(){
        TextView title = mRoot.findViewById(R.id.txtTitle);
        TextView store = mRoot.findViewById(R.id.txtStore);
        TextView price = mRoot.findViewById(R.id.txtPrice);

        title.setText(mOffer.getTitle());
        price.setText(String.format("%.2f", mOffer.getPrice()));
        store.setText(mOffer.getStoreName());
    }

    public interface OnDetailInteractionListener {
        void onCloseButtonClick();

        void onFavoriteButtonClick(Offer offer);
    }
}