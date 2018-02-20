package io.gmartin.deofertas.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.fragments.ImagePagerFragment;
import io.gmartin.deofertas.fragments.SearchFragment;
import io.gmartin.deofertas.fragments.SettingsFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected static final String MAIN_ACTIVITY = "io.gmartin.deofertas.activities.main_activity";
    protected static final String RESULTS_ACTIVITY = "io.gmartin.deofertas.activities.results_activity";
    protected static final String FAVORITES_ACTIVITY = "io.gmartin.deofertas.activities.favorites_activity";
    protected static final String SUGGEST_ACTION = "io.gmartin.deofertas.activities.suggest_action";
    protected static final String SEARCH_ACTION = "io.gmartin.deofertas.activities.search_action";
    protected static final String RESULTS_ACTION = "io.gmartin.deofertas.activities.result_action";
    protected static final String FAVORITES_ACTION = "io.gmartin.deofertas.activities.favorites_action";
    protected static final String SETTINGS_ACTION = "io.gmartin.deofertas.activities.settings_action";
    protected static final String HOME_ACTION = "io.gmartin.deofertas.activities.home_action";
    protected static final String NAVIGATION_INTENT_EXTRA = "io.gmartin.deofertas.activities.navigation_intent_extra";
    protected static final String ACTION = "action";
    protected static final String TITLE_EXTRA = "io.gmartin.deofertas.activities.title_extra";
    protected String mAction;
    protected String mActivity;
    private Boolean mIsPort = null;
    private FragmentManager mFragmentManager;
    protected Toolbar mToolbar;
    protected Menu mMenu;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ACTION, mAction);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mAction = savedInstanceState.getString(ACTION);
        }

        mFragmentManager = getFragmentManager();
    }

    protected void initUI(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        mMenu = menu;
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

        if (id == R.id.action_search) {
            onSearchRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_search) {
            handleSearchNav();
        } else if (id == R.id.nav_favorites) {
            handleFavoritesNav();
        } else if (id == R.id.nav_settings) {
            handleSettingNav();
        } else if (id == R.id.nav_home) {
            handleHomeNav();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Boolean getIsPort() {
        mIsPort = this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        return mIsPort;
    }

    private void handleSearchNav() {

        if (mActivity.equals(MAIN_ACTIVITY)) {
            if(!mAction.equals(SEARCH_ACTION)) {
                mAction = SEARCH_ACTION;
                getSupportActionBar().setTitle(R.string.menu_nav_search);

                SearchFragment search = new SearchFragment();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.container, search);
                transaction.commit();
            }
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(NAVIGATION_INTENT_EXTRA, SEARCH_ACTION);
            startActivity(intent);
        }
    }

    private void handleSettingNav() {
        if (mActivity.equals(MAIN_ACTIVITY)) {
            if(!mAction.equals(SETTINGS_ACTION)) {
                mAction = SETTINGS_ACTION;
                getSupportActionBar().setTitle(R.string.menu_nav_settings);

                SettingsFragment settings = new SettingsFragment();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();

                transaction.replace(R.id.container, settings);
                transaction.addToBackStack("settings");
                transaction.commit();
            }
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(NAVIGATION_INTENT_EXTRA, SETTINGS_ACTION);
            startActivity(intent);
        }
    }

    private void handleFavoritesNav() {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    private void handleHomeNav() {
        if (mActivity.equals(MAIN_ACTIVITY)) {
            if(!mAction.equals(HOME_ACTION)) {
                mAction = HOME_ACTION;
                getSupportActionBar().setTitle(R.string.menu_nav_home);

                ImagePagerFragment pager = new ImagePagerFragment();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.container, pager);
                transaction.commit();
            }
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(NAVIGATION_INTENT_EXTRA, HOME_ACTION);
            startActivity(intent);
        }
    }
}