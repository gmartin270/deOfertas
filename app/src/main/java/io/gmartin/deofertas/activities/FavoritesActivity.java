package io.gmartin.deofertas.activities;

import android.view.View;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.ResultController;

public class FavoritesActivity extends ListActivity{

    @Override
    public void onDataRequested() {
        if(mOffers == null) {
            mList.setProgressBarVisibility(View.VISIBLE);
            mController = new ResultController(this);

            mController.getFavorites();
            getSupportActionBar().setTitle(R.string.menu_nav_favorites);
        }else{
            mList.setOfferList(mOffers);
        }
    }
}
