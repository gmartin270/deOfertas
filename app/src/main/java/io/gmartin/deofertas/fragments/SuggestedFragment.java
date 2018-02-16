package io.gmartin.deofertas.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.adapters.SlidingImageAdapter;
import io.gmartin.deofertas.models.OfferImage;

public class SuggestedFragment extends Fragment {

    public interface OnSuggestedInteractionListener {
        void onDataRequested();
    }

    private Context mContext;
    private View mRoot;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private List<OfferImage> mOfferImages;
    private OnSuggestedInteractionListener mListener;
    private SlidingImageAdapter mAdapter;

    public SuggestedFragment() {
        // Required empty public constructor
    }

    public void setOfferImages(List<OfferImage> offerImages) {
        mOfferImages = offerImages;

        mPager = getActivity().findViewById(R.id.pager);
        mAdapter = new SlidingImageAdapter(getActivity(), mOfferImages);
        //mAdapter.updateList(mOfferImages);
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
        mRoot = inflater.inflate(R.layout.fragment_suggested, container, false);

        mOfferImages = new ArrayList<>();

        if (mContext instanceof OnSuggestedInteractionListener) {
            mListener = (OnSuggestedInteractionListener) mContext;
        }

        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListener.onDataRequested();


    }

    private void init() {
        mPager.setAdapter(mAdapter);

        CirclePageIndicator indicator = getActivity().findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

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
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
