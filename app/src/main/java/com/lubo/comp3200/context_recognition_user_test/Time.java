package com.lubo.comp3200.context_recognition_user_test;

import java.util.Date;

/**
 * Time context parameter.
 * It can either be a specific part of the day, a specifc time and/or date, or
 * a specific time range and/or date
 *
 *
 * Created by Lyubomir on 16/03/2015.
 */

public class Time {

    // Parameters
    private AppParams.PART_OF_DAY mPartOfDay;
    private String mTime;
    private String mRange;
    private Date mDate;
    // Type of Time
    private AppParams.TYPE_OF_TIME mType;
    // The actual user input; will be parsed based on the type
    private String mRawData;

    public Time (AppParams.TYPE_OF_TIME type, String data) {
        mType = type;
        mRawData = data;
    }

    public AppParams.PART_OF_DAY getPartOfDay() {
        return mPartOfDay;
    }

    public AppParams.TYPE_OF_TIME getType() {
        return mType;
    }
}
