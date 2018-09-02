package com.example.android.unescotracker;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.unescotracker.data.SiteContract.SiteEntry;
import com.example.android.unescotracker.utils.Utils;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int SITE_LOADER = 0;

    private Uri mCurrentSiteUri;

    private TextView mNameText;
    private TextView mShortDescText;
    private TextView mUrlText;
    private TextView mLongDescText;
    private TextView mHistoricalTitleText;
    private TextView mHistoricalDescText;
    private TextView mJustificationTitleText;
    private TextView mJustificationText;
    private TextView mLocText;
    private TextView mStatesText;

    private TextView mCategoryView;
    private TextView mRegionView;
    private TextView mDateView;
    private LinearLayout mEndangeredLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Find all relevant views that we will need to read user input from
        mNameText = (TextView) findViewById(R.id.site_name_details_view);
        mShortDescText = (TextView) findViewById(R.id.short_desc_details_view);
        mUrlText = (TextView) findViewById(R.id.url_details_view);
        mLongDescText = (TextView) findViewById(R.id.long_desc_details_view);
        mHistoricalTitleText = (TextView) findViewById(R.id.historical_title_details_view);
        mHistoricalDescText = (TextView) findViewById(R.id.historical_desc_details_view);
        mJustificationTitleText = (TextView) findViewById(R.id.justification_title_details_view);
        mJustificationText = (TextView) findViewById(R.id.justification_details_view);
        mLocText = (TextView) findViewById(R.id.location_details_view);
        mStatesText = (TextView) findViewById(R.id.states_details_view);
        mCategoryView = (TextView) findViewById(R.id.category_details_view);
        mRegionView = (TextView) findViewById(R.id.region_details_view);
        mDateView = (TextView) findViewById(R.id.date_details_view);
        mEndangeredLayout = (LinearLayout) findViewById(R.id.endangered_details_layout);

        mCurrentSiteUri = getIntent().getData();
        getLoaderManager().initLoader(SITE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                SiteEntry._ID,
                SiteEntry.COLUMN_SITE_NAME,
                SiteEntry.COLUMN_SITE_LOCATION,
                SiteEntry.COLUMN_SITE_REGION,
                SiteEntry.COLUMN_SITE_STATES,
                SiteEntry.COLUMN_SITE_TRANSBOUNDARY,
                SiteEntry.COLUMN_SITE_LONG,
                SiteEntry.COLUMN_SITE_LAT,
                SiteEntry.COLUMN_SITE_CATEGORY,
                SiteEntry.COLUMN_SITE_ENDANGERED,
                SiteEntry.COLUMN_SITE_DATE_ADDED,
                SiteEntry.COLUMN_SITE_JUSTIFICATION,
                SiteEntry.COLUMN_SITE_SHORT_DESC,
                SiteEntry.COLUMN_SITE_LONG_DESC,
                SiteEntry.COLUMN_SITE_HISTORICAL_DESC,
                SiteEntry.COLUMN_SITE_URL,
                SiteEntry.COLUMN_SITE_IMAGE_URL,
                SiteEntry.COLUMN_SITE_THUMB,
                SiteEntry.COLUMN_SITE_VISITED,
                SiteEntry.COLUMN_SITE_FAVOURITE
        };

        return new CursorLoader(
                this,
                mCurrentSiteUri,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1){
            return;
        }

        if (cursor.moveToFirst()){
            int nameColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_NAME);
            int locColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_LOCATION);
            int regionColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_REGION);
            int statesColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_STATES);
            int transboundaryColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_TRANSBOUNDARY);
            int longColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_LONG);
            int latColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_LAT);
            int categoryColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_CATEGORY);
            int endangeredColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_ENDANGERED);
            int dateColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_DATE_ADDED);
            int justificationColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_JUSTIFICATION);
            int shortDescColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_SHORT_DESC);
            int longDescColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_LONG_DESC);
            int historicalDescColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_HISTORICAL_DESC);
            int urlColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_URL);
            int imageUrlColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_IMAGE_URL);
            int thumbColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_THUMB);
            int visitedColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_VISITED);
            int favouriteColumnIndex = cursor.getColumnIndex(SiteEntry.COLUMN_SITE_FAVOURITE);


            String name = cursor.getString(nameColumnIndex);
            String shortDesc = cursor.getString(shortDescColumnIndex);
            String url = cursor.getString(urlColumnIndex);
            String longDesc = cursor.getString(longDescColumnIndex);
            String historicalDesc = cursor.getString(historicalDescColumnIndex);
            String justification = cursor.getString(justificationColumnIndex);
            String location = cursor.getString(locColumnIndex);
            String states = cursor.getString(statesColumnIndex);
            int date = cursor.getInt(dateColumnIndex);
            int categoryInt = cursor.getInt(categoryColumnIndex);
            int regionInt = cursor.getInt(regionColumnIndex);
            int endangered = cursor.getInt(endangeredColumnIndex);

            url = "<a href='" + url + "'>" + url + "</a>";

            mNameText.setText(Html.fromHtml(name));

            mCategoryView.setText(Utils.mapIntCategory(categoryInt));
            mRegionView.setText(Utils.mapIntRegion(regionInt));
            mDateView.setText(String.valueOf(date));

            if (endangered == 1){
                mEndangeredLayout.setVisibility(View.GONE);
            }

            mShortDescText.setText(Html.fromHtml(shortDesc).toString().trim());
            mUrlText.setText(Html.fromHtml(url));
            mUrlText.setMovementMethod(LinkMovementMethod.getInstance());
            mUrlText.setLinksClickable(true);
            mLongDescText.setText(Html.fromHtml(longDesc));

            if (!historicalDesc.equals("")){
                mHistoricalDescText.setText(Html.fromHtml(historicalDesc));
            } else {
                mHistoricalDescText.setVisibility(View.GONE);
                mHistoricalTitleText.setVisibility(View.GONE);
            }

            if (!justification.equals("")){
                mJustificationText.setText(Html.fromHtml(justification));
            } else {
                mJustificationText.setVisibility(View.GONE);
                mJustificationTitleText.setVisibility(View.GONE);
            }

            mLocText.setText(Html.fromHtml(location));
            mStatesText.setText(Html.fromHtml(states));

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameText.setText("");
        mShortDescText.setText("");
        mUrlText.setText("");
        mLocText.setText("");
        mStatesText.setText("");
    }
}
