package io.gmartin.deofertas.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.activities.ListActivity;
import io.gmartin.deofertas.adapters.SlidingImageAdapter;
import io.gmartin.deofertas.interfaces.IOrientationLayout;
import io.gmartin.deofertas.models.Offer;
import io.gmartin.deofertas.models.OfferImage;
import io.gmartin.deofertas.utils.Conversions;

public class DetailFragment extends Fragment {

    private final static String DIALOG = "dialog";
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

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int marginTop = Conversions.dpToPx(mContext.getResources().getDimension(R.dimen.detail_image_margin_top), mContext);
                int marginBottom = Conversions.dpToPx(mContext.getResources().getDimension(R.dimen.detail_image_margin_bottom), mContext);
                int marginLeft = Conversions.dpToPx(mContext.getResources().getDimension(R.dimen.detail_image_margin_left), mContext);
                int marginRight = Conversions.dpToPx(mContext.getResources().getDimension(R.dimen.detail_image_margin_right), mContext);
                lp.setMargins(marginLeft, marginTop, marginRight, marginBottom);
                imageView.setLayoutParams(lp);

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

        if (((IOrientationLayout)mContext).getIsPort()) {
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

                    if (mOffer.isFavorite()) {
                        mFavoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                    } else {
                        mFavoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }

                    mListener.onFavoriteButtonClick(mOffer);
                }catch (Exception e){

                }
            }
        });

        ImageButton buyButton = mRoot.findViewById(R.id.buy_button);

        buyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String url = mOffer.getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
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

        if (mOffer.isFavorite()) {
            mFavoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            mFavoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        images_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    SlidingImageAdapter adapter = new SlidingImageAdapter(mContext, mOfferImages);
                    DialogFragment imagePagerFragment = ImagePagerFragment.getInstance(mContext, adapter);
                    imagePagerFragment.show(getChildFragmentManager(), DIALOG);
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