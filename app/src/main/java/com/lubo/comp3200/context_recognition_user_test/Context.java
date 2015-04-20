package com.lubo.comp3200.context_recognition_user_test;

/**
 * Created by Lyubomir on 27/02/2015.
 */
public class Context {

    // Context parameters
    private String mName;
    private AppParams.Activity mActivity;
    private SimpleGeofence mLocation;
    private Time mTime;
    private int mDuration;
    private AppParams.Weather mWeather;
    private AppParams.Temperature mTemperature;
    private AppParams.Season mSeason;

    public Context(String mName,
                   AppParams.Activity mActivity,
                   SimpleGeofence mLocation,
                   Time mTime,
                   int mDuration,
                   AppParams.Weather mWeather,
                   AppParams.Temperature mTemperature,
                   AppParams.Season mSeason) {

        this.mName = mName;
        this.mActivity = mActivity;
        this.mLocation = mLocation;
        this.mTime = mTime;
        this.mDuration = mDuration;
        this.mWeather = mWeather;
        this.mTemperature = mTemperature;
        this.mSeason = mSeason;
    }

    // Alternative constructor for the initial context
    public Context(){

    }

    @Override
    public String toString() {
        return mName;
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
    public AppParams.Weather getWeather() {
        return mWeather;
    }
    public AppParams.Temperature getTemperature() {
        return mTemperature;
    }
    public AppParams.Season getSeason() {
        return mSeason;
    }
    public AppParams.Activity getActivity() {
        return mActivity;
    }

    // Setters
    public void setName(String mName) {
        this.mName = mName;
    }

    public void setActivity(AppParams.Activity mActivity) {
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

    public void setWeather(AppParams.Weather mWeather) {
        this.mWeather = mWeather;
    }

    public void setTemperature(AppParams.Temperature mTemperature) {
        this.mTemperature = mTemperature;
    }

    public void setSeason(AppParams.Season mSeason) {
        this.mSeason = mSeason;
    }

}
