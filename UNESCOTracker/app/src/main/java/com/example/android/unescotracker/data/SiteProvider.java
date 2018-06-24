package com.example.android.unescotracker.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static com.example.android.unescotracker.data.SiteContract.SiteEntry;

/**
 * {@link ContentProvider} for UNESCO Tracker app.
 */
public class SiteProvider extends ContentProvider {
    public SiteDbHelper mDbHelper;

    private static final int SITES = 100;
    private static final int SITES_ID = 101;

    /** Tag for the log messages */
    public static final String LOG_TAG = SiteProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(SiteContract.CONTENT_AUTHORITY, SiteContract.PATH_SITES, SITES);
        sUriMatcher.addURI(SiteContract.CONTENT_AUTHORITY, SiteContract.PATH_SITES + "/#", SITES_ID);
    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new SiteDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case SITES:
                cursor = database.query(SiteEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case SITES_ID:
                selection = SiteEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(SiteEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case SITES:
                return insertSite(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for: " + uri);
        }
    }

    private Uri insertSite(Uri uri, ContentValues values){
        //TODO: Thumnail insert
        String name = values.getAsString(SiteEntry.COLUMN_SITE_NAME);
        Integer region = values.getAsInteger(SiteEntry.COLUMN_SITE_REGION);
        String states = values.getAsString(SiteEntry.COLUMN_SITE_STATES);
        Integer transboundary = values.getAsInteger(SiteEntry.COLUMN_SITE_TRANSBOUNDARY);
        Float longitude = values.getAsFloat(SiteEntry.COLUMN_SITE_LONG);
        Float latitude = values.getAsFloat(SiteEntry.COLUMN_SITE_LAT);
        Integer category = values.getAsInteger(SiteEntry.COLUMN_SITE_CATEGORY);
        Integer endangered = values.getAsInteger(SiteEntry.COLUMN_SITE_ENDANGERED);
        Integer dateAdded = values.getAsInteger(SiteEntry.COLUMN_SITE_DATE_ADDED);
        String shortDesc = values.getAsString(SiteEntry.COLUMN_SITE_SHORT_DESC);
        String url = values.getAsString(SiteEntry.COLUMN_SITE_URL);
        String imageUrl = values.getAsString(SiteEntry.COLUMN_SITE_IMAGE_URL);
        Integer visited = values.getAsInteger(SiteEntry.COLUMN_SITE_VISITED);
        Integer favourite = values.getAsInteger(SiteEntry.COLUMN_SITE_FAVOURITE);

        // Sanity checks
        validateName(name);
        validateRegion(region);
        validateStates(states);
        validateBoolean(transboundary);
        validateCoordinate(longitude);
        validateCoordinate(latitude);
        validateCategory(category);
        validateBoolean(endangered);
        validateDate(dateAdded);
        validateShortDesc(shortDesc);
        validateUrl(url);
        validateUrl(imageUrl);
        validateBoolean(visited);
        validateBoolean(favourite);

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long newRowId = database.insert(SiteEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, newRowId);
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SITES:
                return updateSite(uri, contentValues, selection, selectionArgs);
            case SITES_ID:
                // For the SITE_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = SiteEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateSite(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateSite(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //TODO: Thumnail update
        // Sanity checks
        if (values.containsKey(SiteEntry.COLUMN_SITE_NAME)) {
            String name = values.getAsString(SiteEntry.COLUMN_SITE_NAME);
            validateName(name);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_REGION)) {
            Integer region = values.getAsInteger(SiteEntry.COLUMN_SITE_REGION);
            validateRegion(region);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_STATES)) {
            String states = values.getAsString(SiteEntry.COLUMN_SITE_STATES);
            validateStates(states);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_TRANSBOUNDARY)) {
            Integer transboundary = values.getAsInteger(SiteEntry.COLUMN_SITE_TRANSBOUNDARY);
            validateBoolean(transboundary);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_LONG)) {
            Float longitude = values.getAsFloat(SiteEntry.COLUMN_SITE_LONG);
            validateCoordinate(longitude);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_LAT)) {
            Float latitude = values.getAsFloat(SiteEntry.COLUMN_SITE_LAT);
            validateCoordinate(latitude);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_CATEGORY)) {
            Integer category = values.getAsInteger(SiteEntry.COLUMN_SITE_CATEGORY);
            validateCategory(category);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_ENDANGERED)) {
            Integer endangered = values.getAsInteger(SiteEntry.COLUMN_SITE_ENDANGERED);
            validateBoolean(endangered);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_DATE_ADDED)) {
            Integer dateAdded = values.getAsInteger(SiteEntry.COLUMN_SITE_DATE_ADDED);
            validateDate(dateAdded);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_SHORT_DESC)) {
            String shortDesc = values.getAsString(SiteEntry.COLUMN_SITE_SHORT_DESC);
            validateShortDesc(shortDesc);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_URL)) {
            String url = values.getAsString(SiteEntry.COLUMN_SITE_URL);
            validateUrl(url);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_IMAGE_URL)) {
            String imageUrl = values.getAsString(SiteEntry.COLUMN_SITE_IMAGE_URL);
            validateUrl(imageUrl);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_VISITED)) {
            Integer visited = values.getAsInteger(SiteEntry.COLUMN_SITE_VISITED);
            validateBoolean(visited);
        }

        if (values.containsKey(SiteEntry.COLUMN_SITE_FAVOURITE)) {
            Integer favourite = values.getAsInteger(SiteEntry.COLUMN_SITE_FAVOURITE);
            validateBoolean(favourite);
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowCount = database.update(SiteEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowCount < 1) {
            Log.e(LOG_TAG, "No Sites updated for " + uri);
        } else {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowCount;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SITES:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(SiteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case SITES_ID:
                // Delete a single row given by the ID in the URI
                selection = SiteEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(SiteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted < 1) {
            Log.e(LOG_TAG, "No sites deleted for " + uri);
        } else {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    private void validateName (String name){
        if (name == null) {
            throw new IllegalArgumentException("Site requires a name");
        }
    }

    private void validateStates (String state){
        if (state == null) {
            throw new IllegalArgumentException("Site requires a state");
        }
    }

    private void validateShortDesc (String shortDesc){
        if (shortDesc == null) {
            throw new IllegalArgumentException("Site requires a short description");
        }
    }

    private void validateUrl (String url){
        if (url == null) {
            throw new IllegalArgumentException("Site requires both a site and image url");
        }
    }

    private void validateCategory (Integer category){
        if (category == null || !SiteEntry.isValidCategory(category)){
            throw new IllegalArgumentException("Category must be Natural, Cultural or Mixed");
        }
    }

    private void validateRegion (Integer region){
        if (region == null || !SiteEntry.isValidRegion(region)){
            throw new IllegalArgumentException("Category must be Natural, Cultural or Mixed");
        }
    }

    private void validateCoordinate (Float coordinate){
        if (coordinate == null){
            throw new IllegalArgumentException("Longitude and Latitude must be a decimal number");
        }
    }

    private void validateDate (Integer year){
        if (year == null || year < 0 || String.valueOf(year).length() != 4){
            throw new IllegalArgumentException("Date must be a valid year");
        }
    }

    private void validateBoolean (Integer value){
        if (value == null || !SiteEntry.isValidBoolean(value)){
            throw new IllegalArgumentException("Boolean must be Yes or No");
        }
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SITES:
                return SiteEntry.CONTENT_LIST_TYPE;
            case SITES_ID:
                return SiteEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}