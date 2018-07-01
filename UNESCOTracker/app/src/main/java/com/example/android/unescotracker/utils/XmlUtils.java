package com.example.android.unescotracker.utils;

import android.util.Log;

import com.example.android.unescotracker.SiteItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class XmlUtils {
    private static String LOG_TAG = XmlUtils.class.getName();

    public static void getSitesFromXml (InputStream stream) throws XmlPullParserException, IOException{
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(stream, null);

        int event = parser.getEventType();
        Log.d("XML", "Starting to read doc");
        while (event != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                String name = parser.getName();

                if (name.equals("row")) {
                    SiteItem siteItem = readSite(parser);
                }
            }
            event = parser.next();
        }
        Log.d("XML", "Finished Reading");
    }

    private static SiteItem readSite(XmlPullParser parser) throws XmlPullParserException, IOException {
        String siteName = "";
        String tag = "";
        int event;
        Log.d("XML", "reading new row");

        parser.require(XmlPullParser.START_TAG, null, "row");
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            event = parser.getEventType();
            if (event == XmlPullParser.END_TAG) {
                if (parser.getName().equals("row")) {
                    break;
                }
                continue;
            } else if (event != XmlPullParser.START_TAG) {
                continue;
            }

            tag = parser.getName();
            Log.d("XML", tag);

            switch (tag) {
                case "site":
                    siteName = readText(parser);
                    Log.d("XML", siteName);
                    break;
            }

        }

        return new SiteItem(
                1,
                siteName,
                "location",
                "region",
                "states",
                1,
                11,
                12,
                "Natural",
                0,
                1990,
                "justification",
                "desc",
                "deeeesc",
                "oldDesc",
                "url",
                "iurl",
                "thumb",
                0,
                0
        );
    }

    // For the tags title and summary, extracts their text values.
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    // Make HTTP GET request for JSON data
    public static InputStream makeHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        // Catch null url and return
        if (url==null){
            return inputStream;
        }

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(20000); // Milliseconds
            urlConnection.setConnectTimeout(25000); // Milliseconds
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int response = urlConnection.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK){
                inputStream = urlConnection.getInputStream();
            }else {
                Log.e(LOG_TAG, "HTTP Request Failed, Response Code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "IOException Error: Problem getting JSON data", e);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }

        return inputStream;
    }

    // Return URL object from String URL
    public static URL createUrl(String stringUrl){
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Malformed URL Error: Trying to create URL", e);
        }

        return url;
    }
}
