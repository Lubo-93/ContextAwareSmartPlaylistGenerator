package com.lubo.comp3200.context_aware_smart_playlist_generator;

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
    private AppParams.PartOfDay mPartOfDay;
    private int mTimeHour;
    private int mTimeMins;
    private int mRangeHour;
    private int mRangeMins;
    private int mYear;
    private int mMonth;
    private int mDay;
    // Type of Time
    private AppParams.TypeOfTime mType;

    public Time (AppParams.TypeOfTime type) {
        mType = type;
    }
    
    // Getters
    public AppParams.TypeOfTime getType() {
        return mType;
    }

    public AppParams.PartOfDay getPartOfDay() {
        return mPartOfDay;
    }

    public int getTimeHour() {
        return mTimeHour;
    }

    public int getTimeMins() {
        return mTimeMins;
    }

    public int getRangeHour() {
        return mRangeHour;
    }

    public int getRangeMins() {
        return mRangeMins;
    }

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getDay() {
        return mDay;
    }
    
    // Setters
    public void setPartOfDay(AppParams.PartOfDay mPartOfDay) {
        this.mPartOfDay = mPartOfDay;
    }

    public void setTimeHour(int mTimeHour) {
        this.mTimeHour = mTimeHour;
    }

    public void setTimeMins(int mTimeMins) {
        this.mTimeMins = mTimeMins;
    }

    public void setRangeHour(int mRangeHour) {
        this.mRangeHour = mRangeHour;
    }

    public void setRangeMins(int mRangeMins) {
        this.mRangeMins = mRangeMins;
    }

    public void setYear(int mYear) {
        this.mYear = mYear;
    }

    public void setMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public void setDay(int mDay) {
        this.mDay = mDay;
    }
}
