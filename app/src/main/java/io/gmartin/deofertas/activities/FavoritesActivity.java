package io.gmartin.deofertas.activities;

import android.view.View;
import android.widget.Toast;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.ResultController;
import io.gmartin.deofertas.models.Offer;

public class FavoritesActivity extends ListActivity{

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.menu_nav_favorites);
    }

    @Override
    public void onDataRequested() {
        //getSupportActionBar().setTitle(R.string.menu_nav_favorites);
        mList.setProgressBarVisibility(View.VISIBLE);
        mController = new ResultController(this);

        List<Offer> offers = mController.getFavorites();

        if (offers != null && offers.size() > 0) {
            mList.setOfferList(offers);
            mList.setProgressBarVisibility(View.GONE);
        } else {
            Toast toast = Toast.makeText(this, getResources().getString(R.string.no_data_result), Toast.LENGTH_LONG);
            toast.show();
            finish();
        }

    }
}
