package io.gmartin.deofertas.activities;

import android.view.View;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.ResultController;

public class ResultsActivity extends ListActivity {

    @Override
    public void onDataRequested() {
        if(mOffers == null) {
            mList.setProgressBarVisibility(View.VISIBLE);
            mController = new ResultController(this);

            mController.fetchOffers(mSearch);
            getSupportActionBar().setTitle(R.string.menu_nav_results);
        }else{
            mList.setOfferList(mOffers);
        }
    }
}
