package com.example.android.unescotracker.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class SiteContract {
    private SiteContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.android.UNESCOTracker";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SITES = "sites";

    public static class SiteEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SITES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SITES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SITES;

        public static final String TABLE_NAME = "sites";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SITE_NAME = "site";
        public static final String COLUMN_SITE_LOCATION = "location";
        public static final String COLUMN_SITE_REGION = "region";
        public static final String COLUMN_SITE_STATES = "states";
        public static final String COLUMN_SITE_TRANSBOUNDARY = "transboundary";
        public static final String COLUMN_SITE_LONG = "longitude";
        public static final String COLUMN_SITE_LAT = "latitude";
        public static final String COLUMN_SITE_CATEGORY = "category";
        public static final String COLUMN_SITE_ENDANGERED = "endangered";
        public static final String COLUMN_SITE_DATE_ADDED = "date_added";
        public static final String COLUMN_SITE_JUSTIFICATION = "justification";
        public static final String COLUMN_SITE_SHORT_DESC = "short_desc";
        public static final String COLUMN_SITE_LONG_DESC = "long_desc";
        public static final String COLUMN_SITE_HISTORICAL_DESC = "historical_desc";
        public static final String COLUMN_SITE_URL = "http_url";
        public static final String COLUMN_SITE_IMAGE_URL = "image_url";
        public static final String COLUMN_SITE_THUMB = "thumbnail";
        public static final String COLUMN_SITE_VISITED = "visited";
        public static final String COLUMN_SITE_FAVOURITE = "favourite";

        // Possible values for the Category of the site
        public static final int CATEGORY_NATURAL = 0;
        public static final int CATEGORY_CULTERAL = 1;
        public static final int CATEGORY_MIXED = 2;

        // Possible values for the Region of the site
        public static final int REGION_AFRICA = 0;
        public static final int REGION_ARAB_STATES = 1;
        public static final int REGION_ASIA_AND_PACIFIC = 2;
        public static final int REGION_EU_AND_NA = 3;
        public static final int REGION_LATIN_AMERICA_AND_CARIBBEAN = 4;

        // Possible values for the boolean status of the site
        public static final int BOOLEAN_NO = 0;
        public static final int BOOLEAN_YES = 1;

        public static boolean isValidCategory(int category) {
            if (category == CATEGORY_NATURAL || category == CATEGORY_CULTERAL ||
                    category == CATEGORY_MIXED) {
                return true;
            }
            return false;
        }

        public static boolean isValidRegion(int region) {
            if (region == REGION_AFRICA || region == REGION_ARAB_STATES ||
                    region == REGION_ASIA_AND_PACIFIC || region == REGION_EU_AND_NA ||
                    region == REGION_LATIN_AMERICA_AND_CARIBBEAN) {
                return true;
            }
            return false;
        }

        public static boolean isValidBoolean(int value) {
            if (value == BOOLEAN_NO || value == BOOLEAN_YES) {
                return true;
            }
            return false;
        }
    }
}
