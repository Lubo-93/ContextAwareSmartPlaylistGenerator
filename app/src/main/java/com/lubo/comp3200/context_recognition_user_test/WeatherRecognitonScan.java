package com.lubo.comp3200.context_recognition_user_test;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

/**
 * Gets the current location and requests weather conditions for it
 *
 *
 * Created by Lyubomir on 25/03/2015.
 */
public class WeatherRecognitonScan implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener{

    private LocationClient mLocationClient;
    // Calling activity
    private Activity mActivity;
    // The weather of active context
    private AppParams.WEATHER mCurrentWeather;

    public WeatherRecognitonScan(Activity activityContext, AppParams.WEATHER currentWeather){
        mActivity = activityContext;
        mCurrentWeather = currentWeather;
        mLocationClient = new LocationClient(mActivity, this, this);
    }

    // Starts the process of getting weather information for the current location
    public void startWeatherRecognitionScan() {
        mLocationClient.connect();
    }

    /* When the location client connects, get the last known location and start the
        Weather Recognition Service, passing it the coordinates */
    @Override
    public void onConnected(Bundle bundle) {
        Location currentLocation = mLocationClient.getLastLocation();
        Double lat = currentLocation.getLatitude();
        Double lng = currentLocation.getLongitude();
        Intent intent = new Intent(mActivity, WeatherRecognitionService.class);
        intent.putExtra(AppParams.CURRENT_LAT, lat);
        intent.putExtra(AppParams.CURRENT_LNG, lng);
        intent.putExtra(AppParams.CURRENT_CONTEXT_WEATHER, mCurrentWeather.toString());
        mActivity.startService(intent);
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
