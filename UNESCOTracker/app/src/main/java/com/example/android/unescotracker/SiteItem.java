package com.example.android.unescotracker;

public class SiteItem {
    private int mId;
    private String mName;
    private String mLocation;
    private String mRegion;
    private String mStates;
    private int mTransboundary;
    private Double mLongitude;
    private Double mLatitude;
    private String mCategory;
    private int mEndangered;
    private int mDateAdded;
    private String mJustification;
    private String mShortDesc;
    private String mLongDesc;
    private String mHistoricalDesc;
    private String mUrl;
    private String mImageUrl;
    private String mThumb;
    private int mFavourite;
    private int mVisited;

    public SiteItem(int id, String name, String location, String region, String states,
                    int transboundary, Double longitude, Double latitude, String category,
                    int endangered, int dateAdded, String justification, String shortDesc,
                    String longDesc, String historicalDesc, String url, String imageUrl,
                    String thumb, int visited, int favourite){
        mId = id;
        mName = name;
        mLocation = location;
        mRegion = region;
        mStates = states;
        mTransboundary = transboundary;
        mLongitude = longitude;
        mLatitude = latitude;
        mCategory = category;
        mEndangered = endangered;
        mDateAdded = dateAdded;
        mJustification = justification;
        mShortDesc = shortDesc;
        mLongDesc = longDesc;
        mHistoricalDesc = historicalDesc;
        mUrl = url;
        mImageUrl = imageUrl;
        mThumb = thumb;
        mVisited = visited;
        mFavourite = favourite;
    }

    public int getId() {
        return mId;
    }
    public String getName() {
        return mName;
    }
    public String getLocation() {
        return mLocation;
    }
    public String getRegion() {
        return mRegion;
    }
    public String getStates() {
        return mStates;
    }
    public int getTransboundary() {
        return mTransboundary;
    }
    public Double getLongitude() {
        return mLongitude;
    }
    public Double getLatitude() {
        return mLatitude;
    }
    public String getCategory() {
        return mCategory;
    }
    public int getEndangered() {
        return mEndangered;
    }
    public int getDateAdded() {
        return mDateAdded;
    }
    public String getJustification() {
        return mJustification;
    }
    public String getShortDesc() {
        return mShortDesc;
    }
    public String getLongDesc() {
        return mLongDesc;
    }
    public String getHistoricalDesc() {
        return mHistoricalDesc;
    }
    public String getUrl() {
        return mUrl;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public String getThumb() {
        return mThumb;
    }
    public int getFavourite() {
        return mFavourite;
    }
    public int getVisited() {
        return mVisited;
    }

}
