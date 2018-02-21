package io.gmartin.deofertas.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.BaseController;
import io.gmartin.deofertas.controllers.ResultController;
import io.gmartin.deofertas.fragments.DetailFragment;
import io.gmartin.deofertas.models.Offer;
import io.gmartin.deofertas.models.OfferImage;
import io.gmartin.deofertas.models.Search;
import io.gmartin.deofertas.services.SuggestionsService;

public class SuggestionsActivity extends NavigationActivity
                                 implements BaseController.BaseControllerListener,
                                            ResultController.ResultControllerListener,
                                            DetailFragment.OnDetailInteractionListener {

    private DetailFragment mDetailFragment = new DetailFragment();
    private ResultController mResultController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        mActivity = SUGGESTION_ACTIVITY;
        initUI();

        mResultController = new ResultController(this);


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(SuggestionsService.NOTIFICATION_ID);

        Intent intent = getIntent();
        Offer offer = (Offer) intent.getSerializableExtra(SuggestionsService.SUGGESTION_DATA);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.suggestion_container, mDetailFragment);
        transaction.commit();

        mDetailFragment.setOffer(offer);
    }

    protected void initFragment(){/*Nothing to do*/};

    @Override
    public void onErrorEvent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onImageDataRequested(Long id) {
        mResultController.fetchOfferImages(id);
    }

    @Override
    public void onImageDataReceived(List<OfferImage> offerImages) {
        mDetailFragment.setOfferImages(offerImages);
    }

    @Override
    public void onDataReceived(Object data) {
        //Nothing to do
    }

    @Override
    public void onCloseButtonClick() {
        finish();
    }

    @Override
    public void onFavoriteButtonClick(Offer offer) {
        if (offer.isFavorite()) {
            mResultController.saveFavorite(offer);
        } else {
            mResultController.removeFavorite(offer.getId());
        }
    }
}
