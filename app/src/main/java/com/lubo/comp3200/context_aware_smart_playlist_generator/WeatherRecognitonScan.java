package com.lubo.comp3200.context_aware_smart_playlist_generator;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Gets the current location and requests weather conditions for it
 *
 *
 * Created by Lyubomir on 25/03/2015.
 */
public class WeatherRecognitonScan implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    // Calling activity
    private Activity mActivity;
    // The weather of active context
    private AppParams.Weather mCurrentWeather;

    public WeatherRecognitonScan(Activity activityContext, AppParams.Weather currentWeather){
        mActivity = activityContext;
        mCurrentWeather = currentWeather;
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    // Starts the process of getting weather information for the current location
    public void startWeatherRecognitionScan() {
        mGoogleApiClient.connect();
    }

    /* When the location client connects, get the last known location and start the
        Weather Recognition Service, passing it the coordinates */
    @Override
    public void onConnected(Bundle bundle) {
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Double lat = currentLocation.getLatitude();
        Double lng = currentLocation.getLongitude();
        Intent intent = new Intent(mActivity, WeatherRecognitionService.class);
        intent.putExtra(AppParams.CURRENT_LAT, lat);
        intent.putExtra(AppParams.CURRENT_LNG, lng);
        intent.putExtra(AppParams.CURRENT_CONTEXT_WEATHER, mCurrentWeather.toString());
        mActivity.startService(intent);
    }

    @Override
    public void onConnectionSuspended(int i) {
        
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
