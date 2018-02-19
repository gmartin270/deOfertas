package io.gmartin.deofertas.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.activities.ListActivity;
import io.gmartin.deofertas.models.Offer;
import io.gmartin.deofertas.models.OfferImage;

public class DetailFragment extends Fragment {

    private View mRoot;
    private Offer mOffer = null;
    private OnDetailInteractionListener mListener;
    private Button mCloseBtn;
    private ImageButton mFavoriteButton;
    private Context mContext;
    private List<OfferImage> mOfferImages;

    public DetailFragment() {
        // Required empty public constructor
    }

    public void setOfferImages(List<OfferImage> offerImages) {
        mOfferImages = offerImages;
        LinearLayoutCompat images_layout = mRoot.findViewById(R.id.images_layout);

        if (mOfferImages != null && mOfferImages.size() > 0) {
            for (OfferImage offerImage: mOfferImages) {
                byte[] image = offerImage.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                ImageView imageView = new ImageView(mContext);
                imageView.setImageBitmap(bitmap);
                images_layout.addView(imageView);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mOffer != null) {
            if (mRoot != null) {
                updateOffer();
                mListener.onImageDataRequested(mOffer.getId());
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
        LinearLayoutCompat images_layout = mRoot.findViewById(R.id.images_layout);
        images_layout.removeAllViews();

        title.setText(mOffer.getTitle());
        price.setText(String.format("%.2f", mOffer.getPrice()));
        store.setText(mOffer.getStoreName());

        images_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    DialogFragment newFragment = ImagePagerFragment.getInstance(mContext, mOfferImages);
                    newFragment.show(getChildFragmentManager(), "dialog");

                }catch (Exception e){

                }
            }
        });
    }

    public interface OnDetailInteractionListener {
        void onCloseButtonClick();

        void onFavoriteButtonClick(Offer offer);

        void onImageDataRequested(Long id);
    }
}