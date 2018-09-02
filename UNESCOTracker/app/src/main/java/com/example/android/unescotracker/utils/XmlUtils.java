package com.example.android.unescotracker.utils;

import android.util.Log;

import com.example.android.unescotracker.SiteItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class XmlUtils {
    private static String LOG_TAG = XmlUtils.class.getName();

    public static ArrayList<SiteItem> getSitesFromXml (File xmlFile) throws XmlPullParserException, IOException{
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        InputStream xmlStream = new FileInputStream(xmlFile);

        parser.setInput(xmlStream, null);

        ArrayList<SiteItem> siteList = new ArrayList<>();

        int event = parser.getEventType();
        Log.d("XML", "Starting to read doc");
        while (event != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                String name = parser.getName();

                if (name.equals("row")) {
                    SiteItem siteItem = readSite(parser);
                    siteList.add(siteItem);
                }
            }
            event = parser.next();
        }
        Log.d("XML", "Finished Reading");

        return siteList;
    }

    private static SiteItem readSite(XmlPullParser parser) throws XmlPullParserException, IOException {
        int id = 0;
        String siteName = "";
        String location = "";
        String region = "";
        String states = "";
        int transboundary = 1;
        Double longitude = 0.0;
        Double latitude = 0.0;
        String category = "";
        int endangered = 0;
        int dateAdded = 0;
        String justification = "";
        String shortDesc = "";
        String longDesc = "";
        String historicalDesc = "";
        String url = "";
        String imageUrl = "";
        String thumbnail = "";
        int visited = 0;
        int favourite = 0;

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

            switch (tag) {
                case "unique_number":
                    id = Integer.parseInt(readText(parser));
                    Log.d("XML", String.valueOf(id));
                    break;
                case "site":
                    siteName = readText(parser);
                    Log.d("XML", siteName);
                    break;
                case "location":
                    location = readText(parser);
                    Log.d("XML", location);
                    break;
                case "region":
                    region = readText(parser);
                    Log.d("XML", region);
                    break;
                case "states":
                    states = readText(parser);
                    Log.d("XML", states);
                    break;
                case "transboundary":
                    transboundary = Integer.parseInt(readText(parser));
                    Log.d("XML", String.valueOf(transboundary));
                    break;
                case "longitude":
                    longitude = Double.parseDouble(readText(parser));
                    Log.d("XML", String.valueOf(longitude));
                    break;
                case "latitude":
                    latitude = Double.parseDouble(readText(parser));
                    Log.d("XML", String.valueOf(latitude));
                    break;
                case "category":
                    category = readText(parser);
                    Log.d("XML", category);
                    break;
                case "danger":
                    if (!readText(parser).contains("Y")){
                        endangered = 1;
                        Log.d("XML", "Endangered Site");
                    }
                    break;
                case "date_inscribed":
                    dateAdded = Integer.parseInt(readText(parser));
                    Log.d("XML", String.valueOf(dateAdded));
                    break;
                case "justification":
                    justification = readText(parser);
                    Log.d("XML", justification);
                    break;
                case "short_description":
                    shortDesc = readText(parser);
                    Log.d("XML", shortDesc);
                    break;
                case "long_description":
                    longDesc = readText(parser);
                    Log.d("XML", longDesc);
                    break;
                case "historical_description":
                    historicalDesc = readText(parser);
                    Log.d("XML", historicalDesc);
                    break;
                case "http_url":
                    url = readText(parser);
                    Log.d("XML", url);
                    break;
                case "image_url":
                    imageUrl = readText(parser);
                    Log.d("XML", imageUrl);
                    break;
            }

        }

        return new SiteItem(
                id,
                siteName,
                location,
                region,
                states,
                transboundary,
                longitude,
                latitude,
                category,
                endangered,
                dateAdded,
                justification,
                shortDesc,
                longDesc,
                historicalDesc,
                url,
                imageUrl,
                thumbnail,
                visited,
                favourite
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

    // Make HTTP GET request for JSON data
    public static File makeHttpRequest(URL url, File baseDir) throws IOException {
        HttpURLConnection urlConnection = null;
        File downloadedFile = null;

        // Catch null url and return
        if (url==null){
            return null;
        }

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(20000); // Milliseconds
            urlConnection.setConnectTimeout(25000); // Milliseconds
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int response = urlConnection.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK){
                downloadedFile = new File(baseDir.getAbsolutePath() + "/unescoSite.xml");

                FileOutputStream fileOutput = new FileOutputStream(downloadedFile, false);

                InputStream inputStream = urlConnection.getInputStream();

                byte[] buffer = new byte[1024];
                int bufferLength = 0; //used to store a temporary size of the buffer

                // TODO: Progress bar

                while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                    fileOutput.write(buffer, 0, bufferLength);
                }

                fileOutput.close();
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

        return downloadedFile;
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
