package com.example.android.unescotracker;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.unescotracker.data.DatabaseManager;
import com.example.android.unescotracker.data.SiteContract.SiteEntry;
import com.example.android.unescotracker.utils.Utils;
import com.example.android.unescotracker.utils.XmlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SiteListFragment.FragmentListListener{
    private static String LOG_TAG = "UNESCO_Tracker";

    SiteListFragment mSiteListFragment;
    public SimpleFragmentPagerAdapter mAdapter;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.menu_all_items:
                                menuItem.setChecked(true);
                                return true;
                            case R.id.menu_favourited:
                                menuItem.setChecked(true);
                                return true;
                            case R.id.menu_visited:
                                menuItem.setChecked(true);
                                return true;
                            case R.id.menu_settings:
                                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                                startActivity(settingsIntent);
                                return true;
                        }

                        return false;
                    }
                });

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_widget);
        tabLayout.setupWithViewPager(viewPager);
    }

    public Fragment getFragmentAtPos(int pos){
        return mAdapter.getRegisteredFragment(pos);
    }

    //-------------------------------------------------------------------------------------------
    /* Menu Methods */
    //-------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment page = getFragmentAtPos(0);
        mSiteListFragment = (SiteListFragment) page;

        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.search_button:
                //Todo: add search functionality
                return true;
            case R.id.filter_button:
                //Todo: add filter functionality
                return false;
            case R.id.sort_button:
                //Todo: add sort functionality
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
