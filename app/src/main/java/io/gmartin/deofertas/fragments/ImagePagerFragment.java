package io.gmartin.deofertas.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.adapters.SlidingImageAdapter;
import io.gmartin.deofertas.models.OfferImage;

public class ImagePagerFragment extends DialogFragment {

    private static Context mContext;
    private ViewPager mPager;
    private CirclePageIndicator mIndicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static List<OfferImage> mOfferImages;
    private SlidingImageAdapter mAdapter;
    private static ImagePagerFragment mInstance;

    public static ImagePagerFragment getInstance(Context context, List<OfferImage> images) {
        if (mInstance == null) {
            mInstance = new ImagePagerFragment();
        }

        mContext = context;
        mOfferImages = images;

        return mInstance;
    }

    public ImagePagerFragment() {
        // Required empty public constructor
    }

    public void setOfferImages(List<OfferImage> offerImages) {
        mOfferImages = offerImages;

        mAdapter = new SlidingImageAdapter(getActivity(), mOfferImages);
        init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggested, container, false);
        mPager = view.findViewById(R.id.pager);
        mIndicator = view.findViewById(R.id.indicator);

        if(mOfferImages != null) {
            setOfferImages(mOfferImages);
        }

        return view;
    }

    private void init() {
        mPager.setAdapter(mAdapter);

        mIndicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        mIndicator.setRadius(5 * density);

        NUM_PAGES = mOfferImages.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }
}
