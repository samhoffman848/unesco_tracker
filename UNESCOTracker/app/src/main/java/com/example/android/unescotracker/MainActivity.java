package com.example.android.unescotracker;

import android.content.ContentValues;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.unescotracker.data.SiteContract.SiteEntry;
import com.example.android.unescotracker.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SiteListFragment.FragmentListListener{
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
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_widget);
        tabLayout.setupWithViewPager(viewPager);

        //createDummyData();
    }

    private void createDummyData(){
        ArrayList<SiteItem> dummyData = new ArrayList<>(
                Arrays.asList(
                        new SiteItem(1, "Roman Colissuem", "Rome", "Europe and North America", "Italy", 0, 12, 10, "Cultural", 1, 1934, "la la la la la la la la la", "This is some text", "", "", "www.someaddress.com", "www.someaddress.com/image.jpg", "", 1, 0),
                        new SiteItem(2, "Eiffel Tower", "Paris", "Europe and North America", "France", 0, 12, 10, "Cultural", 1, 1945, "", "This is some text", "this is a longer bit of text 123456789", "", "www.someaddress.com", "www.someaddress.com/image.jpg", "", 0, 1),
                        new SiteItem(3, "Sphinx", "Cairo", "Africa", "Egypt", 0, 12, 10, "Mixed", 1, 2011, "", "This is some text", "", "a long time ago in a galaxy far far away there was a world heritage site", "www.someaddress.com", "www.someaddress.com/image.jpg", "", 1, 0),
                        new SiteItem(4, "Great Barrier Reef", "", "Asia and the Pacific", "place1, place2", 1, 12, 10, "Natural", 1, 1763, "", "This is some text", "", "", "www.someaddress.com", "www.someaddress.com/image.jpg", "", 0, 1),
                        new SiteItem(5, "Grand Canyon", "", "Europe and North America", "some, where, more, locations", 1, 12, 10, "Natural", 1, 2001, "", "This is some text", "", "", "www.someaddress.com", "www.someaddress.com/image.jpg", "", 0, 1),
                        new SiteItem(6, "Ancient and Primeval Beech Forests of the Carpathians and Other Regions of Europe", "", "Europe and North America", "Albania,Austria,Belgium,Bulgaria,Croatia,Germany,Italy,Romania,Slovakia,Slovenia,Spain,Ukraine", 1, 12, 10, "Natural", 1, 2001, "", "<p><span>This transboundary property stretches over 12 countries. Since the end of the last Ice Age, European Beech spread from a few isolated refuge areas in the Alps, Carpathians</span><span>, Dinarides</span><span>, Mediterranean and Pyrenees over a short period of a few thousand years in a process that is still ongoing. The successful expansion across a whole continent is related to the tree’s </span><span>adaptability and tolerance of different climatic, geographical and physical conditions. </span></p>", "", "", "www.someaddress.com", "www.someaddress.com/image.jpg", "", 0, 1),
                        new SiteItem(7, "Qhapaq Ñan, Andean Road System", "", "Latin America and the Caribbean", "some, where, more, locations", 1, 12, 10, "Natural", 1, 2001, "", "This is some text", "", "", "www.someaddress.com", "www.someaddress.com/image.jpg", "", 0, 0),
                        new SiteItem(8, "Historic Town of Guanajuato and Adjacent Mines", "Etat de: Guanajuato. Municipalite: Guanajuato", "Latin America and the Caribbean", "Mexico", 0, 12, 10, "Natural", 1, 2001, "", "This is some text", "", "", "www.someaddress.com", "www.someaddress.com/image.jpg", "", 0, 0),
                        new SiteItem(9, "Levoča, Spišský Hrad and the Associated Cultural Monuments", "", "Europe and North America", "Slovakia", 0, 12, 10, "Natural", 1, 2001, "", "This is some text", "", "", "www.someaddress.com", "www.someaddress.com/image.jpg", "", 1, 1)
                )
        );

        for (int i = 0; i < dummyData.size(); i++){
            ContentValues values = new ContentValues();

            SiteItem item = dummyData.get(i);

            int region = Utils.mapStringRegion(item.getRegion());
            int category = Utils.mapStringCategory(item.getCategory());

            values.put(SiteEntry._ID, item.getId());
            values.put(SiteEntry.COLUMN_SITE_NAME, item.getName());
            values.put(SiteEntry.COLUMN_SITE_LOCATION, item.getLocation());
            values.put(SiteEntry.COLUMN_SITE_REGION, region);
            values.put(SiteEntry.COLUMN_SITE_STATES, item.getStates());
            values.put(SiteEntry.COLUMN_SITE_TRANSBOUNDARY, item.getTransboundary());
            values.put(SiteEntry.COLUMN_SITE_LONG, item.getLongitude());
            values.put(SiteEntry.COLUMN_SITE_LAT, item.getLatitude());
            values.put(SiteEntry.COLUMN_SITE_CATEGORY, category);
            values.put(SiteEntry.COLUMN_SITE_ENDANGERED, item.getEndangered());
            values.put(SiteEntry.COLUMN_SITE_DATE_ADDED, item.getDateAdded());
            values.put(SiteEntry.COLUMN_SITE_JUSTIFICATION, item.getJustification());
            values.put(SiteEntry.COLUMN_SITE_SHORT_DESC, item.getShortDesc());
            values.put(SiteEntry.COLUMN_SITE_LONG_DESC, item.getLongDesc());
            values.put(SiteEntry.COLUMN_SITE_HISTORICAL_DESC, item.getHistoricalDesc());
            values.put(SiteEntry.COLUMN_SITE_URL, item.getUrl());
            values.put(SiteEntry.COLUMN_SITE_IMAGE_URL, item.getImageUrl());
            values.put(SiteEntry.COLUMN_SITE_THUMB, item.getThumb());
            values.put(SiteEntry.COLUMN_SITE_VISITED, item.getVisited());
            values.put(SiteEntry.COLUMN_SITE_FAVOURITE, item.getFavourite());

            Uri newUri = getContentResolver().insert(SiteEntry.CONTENT_URI, values);
        }

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
                return true;
            case R.id.sort_button:
                //Todo: add sort functionality
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
