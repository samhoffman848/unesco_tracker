package com.example.android.unescotracker.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.unescotracker.utils.XmlUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DatabaseManager{
    private static String LOG_TAG = DatabaseManager.class.getName();

    private static String XMLSTREAM = "http://whc.unesco.org/en/list/xml/";

    public static void runDatabaseManager(Boolean isConnected){
        if (isConnected) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    URL url = XmlUtils.createUrl(XMLSTREAM);
                    InputStream stream = null;

                    try {
                        stream = XmlUtils.makeHttpRequest(url);
                    } catch (IOException e){
                        Log.e(LOG_TAG, "IOException Error: Trying to get URL stream", e);
                    }

                    updateDatabase(stream);
                }
            });
        }

    }

    private static void updateDatabase(InputStream stream){
        try {
            XmlUtils.getSitesFromXml(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "IOException Error: Trying to close xml stream", e);
                }
            }
        }
    }
}
