package io.gmartin.deofertas.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.models.OfferImage;
import io.gmartin.deofertas.models.SuggestedOffer;

public class SlidingSuggestionAdapter extends PagerAdapter {

    //https://demonuts.com/2017/03/17/image-slider-with-slideshow-using-viewpager-android/

    private List<SuggestedOffer> mSuggestedOffers = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public SlidingSuggestionAdapter(Context context, List<SuggestedOffer> suggestedOffers) {
        this.mContext = context;
        mSuggestedOffers = suggestedOffers;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mSuggestedOffers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = mInflater.inflate(R.layout.sliding_image_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.offer_img);

        if (mSuggestedOffers != null && mSuggestedOffers.get(position).getImageStr() != null) {
            byte[] image = mSuggestedOffers.get(position).getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView.setImageBitmap(bitmap);
        }

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}