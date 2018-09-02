package com.example.android.unescotracker.data;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.unescotracker.AsyncXmlReader;
import com.example.android.unescotracker.SiteItem;
import com.example.android.unescotracker.UnescoApplication;
import com.example.android.unescotracker.utils.Utils;
import com.example.android.unescotracker.utils.XmlUtils;

import java.io.File;
import java.util.ArrayList;

public class DatabaseManager{
    private static String XMLSTREAM = "http://whc.unesco.org/en/list/xml/";

    public static void runDatabaseManager(Boolean isConnected, final File baseDir){
        if (isConnected) {
            AsyncXmlReader asyncXmlRead = new AsyncXmlReader(XMLSTREAM, baseDir);
            asyncXmlRead.execute();
        }

    }

    public static ArrayList<SiteItem> updateDatabase(File xmlFile){
        ArrayList<SiteItem> siteList;

        try {
            siteList = XmlUtils.getSitesFromXml(xmlFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return siteList;
    }

    public static void writeSiteToDatabase(SiteItem item){
        ContentValues values = new ContentValues();
        //TODO: Clean up html on database import
        //TODO: If site exists just update it
        int region = Utils.mapStringRegion(item.getRegion());
        int category = Utils.mapStringCategory(item.getCategory());

        values.put(SiteContract.SiteEntry._ID, item.getId());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_NAME, item.getName());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_LOCATION, item.getLocation());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_REGION, region);
        values.put(SiteContract.SiteEntry.COLUMN_SITE_STATES, item.getStates());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_TRANSBOUNDARY, item.getTransboundary());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_LONG, item.getLongitude());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_LAT, item.getLatitude());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_CATEGORY, category);
        values.put(SiteContract.SiteEntry.COLUMN_SITE_ENDANGERED, item.getEndangered());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_DATE_ADDED, item.getDateAdded());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_JUSTIFICATION, item.getJustification());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_SHORT_DESC, item.getShortDesc());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_LONG_DESC, item.getLongDesc());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_HISTORICAL_DESC, item.getHistoricalDesc());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_URL, item.getUrl());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_IMAGE_URL, item.getImageUrl());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_THUMB, item.getThumb());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_VISITED, item.getVisited());
        values.put(SiteContract.SiteEntry.COLUMN_SITE_FAVOURITE, item.getFavourite());

        Uri newUri = UnescoApplication.getAppContext().getContentResolver().insert(SiteContract.SiteEntry.CONTENT_URI, values);
    }
}
