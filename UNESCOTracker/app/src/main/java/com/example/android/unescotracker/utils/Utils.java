package com.example.android.unescotracker.utils;

public final class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static String mapIntRegion(int region){
        switch (region){
            case 0:
                return "Africa";
            case 1:
                return "Arab States";
            case 2:
                return "Asia and the Pacific";
            case 3:
                return "Europe and North America";
            case 4:
                return "Latin America and the Caribbean";
            default:
                return null;
        }
    }

    public static int mapStringRegion(String region){
        switch (region){
            case "Africa":
                return 0;
            case "Arab States":
                return 1;
            case "Asia and the Pacific":
                return 2;
            case "Europe and North America":
                return 3;
            case "Latin America and the Caribbean":
                return 4;
            default:
                return 5;
        }
    }

    public static int mapStringCategory(String category){
        switch (category){
            case "Natural":
                return 0;
            case "Cultural":
                return 1;
            case "Mixed":
                return 2;
            default:
                return 3;
        }
    }
}
