package com.example.android.unescotracker;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.android.unescotracker.data.DatabaseManager;
import com.example.android.unescotracker.utils.XmlUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncXmlReader extends AsyncTask {
    private static String LOG_TAG = AsyncXmlReader.class.getName();

    private File mBaseDir;
    private String mXmlStream;

    public AsyncXmlReader(String xmlStream, File baseDir){
        mBaseDir = baseDir;
        mXmlStream = xmlStream;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        URL url = XmlUtils.createUrl(mXmlStream);
        File xmlFile = null;

        try {
            xmlFile = XmlUtils.makeHttpRequest(url, mBaseDir);
        } catch (IOException e){
            Log.e(LOG_TAG, "IOException Error: Trying to get URL stream", e);
        }

        ArrayList<SiteItem> siteList = DatabaseManager.updateDatabase(xmlFile);

        for (int i = 0; i < siteList.size(); i++){
            SiteItem item = siteList.get(i);
            DatabaseManager.writeSiteToDatabase(item);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Toast.makeText(UnescoApplication.getAppContext(), R.string.finished_updating_db, Toast.LENGTH_SHORT).show();
    }
}
