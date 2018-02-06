package io.gmartin.deofertas.activities;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.gmartin.deofertas.R;

public class NavigationActivity extends Activity {
    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;

    protected void initUI() {
        String[] test = {"uno","dos","tres"};
        mDrawerList = findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, test));

        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }
}