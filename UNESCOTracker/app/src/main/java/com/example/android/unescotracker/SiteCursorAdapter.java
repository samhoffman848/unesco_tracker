package com.example.android.unescotracker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.unescotracker.data.SiteContract.SiteEntry;
import com.example.android.unescotracker.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * {@link SiteCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class SiteCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link SiteCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public SiteCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameView = (TextView) view.findViewById(R.id.site_name_view);
        TextView locationView = (TextView) view.findViewById(R.id.site_location_view);
        TextView distanceView = (TextView) view.findViewById(R.id.distance_view);
        ToggleButton favouriteToggle = (ToggleButton) view.findViewById(R.id.favourite_toggle);
        ToggleButton visitedToggle = (ToggleButton) view.findViewById(R.id.visited_toggle);
        CircleImageView thumbnailView = (CircleImageView) view.findViewById(R.id.thumbnail_view);

        String siteName = cursor.getString(cursor.getColumnIndex(SiteEntry.COLUMN_SITE_NAME));
        Integer siteTransboundary = cursor.getInt(cursor.getColumnIndex(SiteEntry.COLUMN_SITE_TRANSBOUNDARY));
        Integer siteFavourite = cursor.getInt(cursor.getColumnIndex(SiteEntry.COLUMN_SITE_FAVOURITE));
        Integer siteVisited = cursor.getInt(cursor.getColumnIndex(SiteEntry.COLUMN_SITE_VISITED));
        String siteThumbnail = cursor.getString(cursor.getColumnIndex(SiteEntry.COLUMN_SITE_THUMB));
        String siteLocation;

        // Get state or region based on transboundary
        if (siteTransboundary == SiteEntry.BOOLEAN_YES){
            Integer siteRegion = cursor.getInt(cursor.getColumnIndex(SiteEntry.COLUMN_SITE_REGION));
            siteLocation = Utils.mapIntRegion(siteRegion);
        } else {
            siteLocation = cursor.getString(cursor.getColumnIndex(SiteEntry.COLUMN_SITE_STATES));
        }

        // Convert visited and favourite to Booleans
        Boolean favouriteBool = siteFavourite == 1;
        Boolean visitedBool = siteVisited == 1;

        nameView.setText(siteName);
        locationView.setText(siteLocation);
        favouriteToggle.setChecked(favouriteBool);
        visitedToggle.setChecked(visitedBool);

        //todo: update thumbnail
        //todo: update distance
    }
}