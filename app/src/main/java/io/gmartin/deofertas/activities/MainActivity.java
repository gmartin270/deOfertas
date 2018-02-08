package io.gmartin.deofertas.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.fragments.ResultsFragment;
import io.gmartin.deofertas.fragments.SearchFragment;
import io.gmartin.deofertas.models.Search;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   SearchFragment.OnSearchInteractionListener {

    public static final String SEARCH_STATE = "io.gmartin.deofertas.activities.search_state";
    public static final String RESULT_STATE = "io.gmartin.deofertas.activities.result_state";
    private FragmentManager mManager;
    private SearchFragment mSearch = new SearchFragment();
    private ResultsFragment mResults = new ResultsFragment();
    private String mState;
    private Boolean mIsPort = null;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("state", mState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mState = savedInstanceState.getString("state");
        }

        setContentView(R.layout.activity_main);
        View container = findViewById(R.id.container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mIsPort = container!=null;

        mManager = getFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();

        if (mState == null || mState.equals(SEARCH_STATE)) {
            mState = SEARCH_STATE;
            transaction.replace(R.id.container, mSearch);
        } else if(mState.equals(RESULT_STATE)) {
            transaction.replace(R.id.container, mResults);
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchButtonClick(Search search) {
        mResults.getData(search);

        mState = RESULT_STATE;
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.replace(R.id.container, mResults);
        transaction.commit();
    }

    public Boolean getIsPort() {
        return mIsPort;
    }
}
