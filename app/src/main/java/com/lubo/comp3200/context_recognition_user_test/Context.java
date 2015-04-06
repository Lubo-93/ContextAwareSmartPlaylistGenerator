package com.lubo.comp3200.context_recognition_user_test;

/**
 * Created by Lyubomir on 27/02/2015.
 */
public class Context {

    // Context parameters
    private String mName;
    private AppParams.ACTIVITY mActivity;
    private SimpleGeofence mLocation;
    private Time mTime;
    private AppParams.PART_OF_DAY mPartOfDay;
    private int mDuration;
    private AppParams.WEATHER mWeather;
    private AppParams.TEMPERATURE mTemperature;
    private AppParams.SEASON mSeason;


    public Context(String mName,
                   AppParams.ACTIVITY mActivity,
                   SimpleGeofence mLocation,
                   Time mTime,
                   int mDuration,
                   AppParams.WEATHER mWeather,
                   AppParams.TEMPERATURE mTemperature,
                   AppParams.SEASON mSeason) {

        this.mName = mName;
        this.mActivity = mActivity;
        this.mLocation = mLocation;
        this.mTime = mTime;
        this.mDuration = mDuration;
        this.mWeather = mWeather;
        this.mTemperature = mTemperature;
        this.mSeason = mSeason;
        if (mTime.getType() == AppParams.TYPE_OF_TIME.valueOf("DAY_SECTION")) {
            mPartOfDay = mTime.getPartOfDay();
        }else {
            mPartOfDay = null;
        }
    }

    // Alternative constructor for the initial context
    public Context(){

    }
    
    // Getters
    public String getName(){
        return mName;
    }
    public SimpleGeofence getLocation() {
        return mLocation;
    }
    public Time getTime() {
        return mTime;
    }
    public int getDuration() {
        return mDuration;
    }
    public AppParams.WEATHER getWeather() {
        return mWeather;
    }
    public AppParams.TEMPERATURE getTemperature() {
        return mTemperature;
    }
    public AppParams.SEASON getSeason() {
        return mSeason;
    }
    public AppParams.ACTIVITY getActivity() {
        return mActivity;
    }

    // Setters

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setActivity(AppParams.ACTIVITY mActivity) {
        this.mActivity = mActivity;
    }

    public void setLocation(SimpleGeofence mLocation) {
        this.mLocation = mLocation;
    }

    public void setTime(Time mTime) {
        this.mTime = mTime;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public void setWeather(AppParams.WEATHER mWeather) {
        this.mWeather = mWeather;
    }

    public void setTemperature(AppParams.TEMPERATURE mTemperature) {
        this.mTemperature = mTemperature;
    }

    public void setSeason(AppParams.SEASON mSeason) {
        this.mSeason = mSeason;
    }
}
