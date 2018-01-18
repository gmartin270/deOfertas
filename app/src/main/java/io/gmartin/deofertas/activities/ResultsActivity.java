package io.gmartin.deofertas.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.fragments.DetailFragment;
import io.gmartin.deofertas.fragments.ListFragment;
import io.gmartin.deofertas.models.Item;
import io.gmartin.deofertas.services.APIClientService;

public class ResultsActivity extends Activity
                            implements ListFragment.OnOffersListInteractionListener,
                                       DetailFragment.OnDetailInteractionListener {

    FragmentManager mManager;
    ListFragment mList = new ListFragment();
    DetailFragment mDetail = new DetailFragment();
    private Boolean mIsPort = null;
    private APIClientService.APIBinder mBinder;

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (APIClientService.APIBinder)service;
            mBinder.setContext(ResultsActivity.this);
            mBinder.searchOffers();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder = null;
        }
    };

    public Boolean getIsPort() {
        return mIsPort;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent i = new Intent(this, APIClientService.class);
        bindService(i, mConnection, BIND_AUTO_CREATE);

        View container = findViewById(R.id.container);
        mIsPort = container!=null;
        mManager = getFragmentManager();

        FragmentTransaction transaction = mManager.beginTransaction();

        if(mIsPort) {
            transaction.replace(R.id.container, mList);
            transaction.commit();
        } else {
            transaction.replace(R.id.listContainer, mList);
            transaction.replace(R.id.detailContainer, mDetail);
            transaction.commit();
        }
    }

    @Override
    public void onSelectedItem(Object item) {
        if (mIsPort) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.container, mDetail);
            transaction.commit();
        }

        if (item != null) {
            mDetail.setItem((Item) item);
        }
    }

    @Override
    public void onCloseButtonClick() {
        if(mIsPort) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.container, mList);
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
