package com.example.android.unescotracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.unescotracker.data.SiteContract.SiteEntry;

public class SiteDbHelper extends SQLiteOpenHelper {
    // Database file name
    private static final String DATABASE_NAME = "unesco_sites.db";

    // Database version. If you change the schema you must increment that db version.
    private static final int DATABASE_VERSION = 1;

    public SiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_SITES_TABLE = "CREATE TABLE " + SiteEntry.TABLE_NAME + " (" +
                SiteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SiteEntry.COLUMN_SITE_NAME + " TEXT NOT NULL, " +
                SiteEntry.COLUMN_SITE_LOCATION + " TEXT, " +
                SiteEntry.COLUMN_SITE_REGION + " INTEGER NOT NULL, " +
                SiteEntry.COLUMN_SITE_STATES + " TEXT NOT NULL, " +
                SiteEntry.COLUMN_SITE_TRANSBOUNDARY + " INTEGER NOT NULL DEFAULT 0, " +
                SiteEntry.COLUMN_SITE_LONG + " REAL NOT NULL, " +
                SiteEntry.COLUMN_SITE_LAT + " REAL NOT NULL, " +
                SiteEntry.COLUMN_SITE_CATEGORY + " INTEGER NOT NULL, " +
                SiteEntry.COLUMN_SITE_ENDANGERED + " INTEGER NOT NULL DEFAULT 0, " +
                SiteEntry.COLUMN_SITE_DATE_ADDED + " INTEGER NOT NULL, " +
                SiteEntry.COLUMN_SITE_JUSTIFICATION + " TEXT, " +
                SiteEntry.COLUMN_SITE_SHORT_DESC + " TEXT NOT NULL, " +
                SiteEntry.COLUMN_SITE_LONG_DESC + " TEXT, " +
                SiteEntry.COLUMN_SITE_HISTORICAL_DESC + " TEXT, " +
                SiteEntry.COLUMN_SITE_URL + " TEXT NOT NULL, " +
                SiteEntry.COLUMN_SITE_IMAGE_URL + " TEXT NOT NULL, " +
                SiteEntry.COLUMN_SITE_THUMB + " BLOB, " +
                SiteEntry.COLUMN_SITE_VISITED + " INTEGER NOT NULL DEFAULT 0, " +
                SiteEntry.COLUMN_SITE_FAVOURITE + " INTEGER NOT NULL DEFAULT 0" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_SITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // To implement later
    }
}
