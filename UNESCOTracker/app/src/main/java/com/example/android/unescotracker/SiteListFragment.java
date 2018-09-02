package com.example.android.unescotracker;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.unescotracker.data.SiteContract.SiteEntry;
import com.example.android.unescotracker.data.SiteProvider;

public class SiteListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int SITE_LOADER = 0;

    String cursorFilter;
    SiteCursorAdapter mAdapter;
    OnSiteListener mCallback;
    private TextView mEmptyListTextView;

    public static final String LOG_TAG = MainActivity.class.getName();

    public SiteListFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_site_fragment, container, false);
        Log.d("Tracker App", "here!");
        ListView siteListView = (ListView) rootView.findViewById(R.id.site_list);

        mEmptyListTextView = (TextView) getActivity().findViewById(R.id.empty_view);
        siteListView.setEmptyView(mEmptyListTextView);

        mAdapter = new SiteCursorAdapter(getActivity(), null);
        mCallback.onCursorQuery(mAdapter);
        siteListView.setAdapter(mAdapter);
        siteListView.setTextFilterEnabled(true);

        siteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Log.d("Tracker App", "clicked!");
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                Uri currentSiteUri = ContentUris.withAppendedId(SiteEntry.CONTENT_URI, id);
                intent.setData(currentSiteUri);
                startActivity(intent);
            }
        });

        mAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                Log.d("Tracker App", constraint.toString());
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

                String selection = SiteEntry.COLUMN_SITE_NAME + " LIKE ? OR " +
                        SiteEntry.COLUMN_SITE_LOCATION + " LIKE ? OR " +
                        SiteEntry.COLUMN_SITE_STATES + " LIKE ? OR " +
                        SiteEntry.COLUMN_SITE_NAME + " LIKE ? OR " +
                        SiteEntry.COLUMN_SITE_LOCATION + " LIKE ? OR " +
                        SiteEntry.COLUMN_SITE_STATES + " LIKE ?";

                String[] selectionArgs = {
                        "% " + constraint.toString() + "%",
                        "% " + constraint.toString() + "%",
                        "% " + constraint.toString() + "%",
                        constraint.toString() + "%",
                        constraint.toString() + "%",
                        constraint.toString() + "%"
                };

                return getActivity().getContentResolver().query(SiteEntry.CONTENT_URI, projection, selection, selectionArgs, null);
            }
        });

        getLoaderManager().initLoader(SITE_LOADER, null, this);

        return rootView;
    }

    // Container Activity must implement this interface
    public interface OnSiteListener {
        public void onCursorQuery(SiteCursorAdapter cursorAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnSiteListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSiteListener");
        }
    }

    public void queryList(String query){
        mAdapter.getFilter().filter(query);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // ToDo: Tidy up, should only actually need to query 1 or 2 things
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

        return new CursorLoader(getActivity(),
                SiteEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
