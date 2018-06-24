package com.example.android.unescotracker;



import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.unescotracker.data.SiteContract.SiteEntry;

public class SiteListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private FragmentListListener fragmentListListener;

    private static final int SITE_LOADER = 0;

    SiteCursorAdapter mAdapter;

    private TextView mEmptyListTextView;

    public static final String LOG_TAG = MainActivity.class.getName();

    public SiteListFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_site_fragment, container, false);

        ListView petListView = (ListView) rootView.findViewById(R.id.site_list);

        mEmptyListTextView = (TextView) getActivity().findViewById(R.id.empty_view);
        petListView.setEmptyView(mEmptyListTextView);

        mAdapter = new SiteCursorAdapter(getActivity(), null);
        petListView.setAdapter(mAdapter);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //todo: open details view
            }
        });

        getLoaderManager().initLoader(SITE_LOADER, null, this);

        return rootView;
    }

    public interface FragmentListListener{
        public Fragment getFragmentAtPos(int pos);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentListListener = (FragmentListListener) context;
        } catch (ClassCastException castException) {
            Log.e(LOG_TAG, "Activity failed to implement listener", castException);
        }
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