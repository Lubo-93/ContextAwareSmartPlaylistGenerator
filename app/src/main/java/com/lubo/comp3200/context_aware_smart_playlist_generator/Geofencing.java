package com.lubo.comp3200.context_aware_smart_playlist_generator;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

/**
 * Created by Lyubomir on 21/04/2015.
 *
 * Handles adding and removing geofences
 *
 * Code influenced by Google Examples
 */

public class Geofencing implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    // Tag for logging
    private static final String APPTAG = "Geofencing";
    // Logger
    private Logger mLog;
    // GoogleApiClient for requesting location services
    private GoogleApiClient mGoogleApiClient;
    // Used to keep track of whether geofences were added
    private boolean mGeofencesAdded;
    // Used for adding and removing of geofences requests
    private PendingIntent mGeofencePendingIntent;
    // Used to persist application state about whether geofences were added
    private SharedPreferences mSharedPreferences;
    // Reference to calling activity
    private Activity mCallingActivity;
    // Indicate whether it's an add or remove request type
    private AppParams.REQUEST_TYPE mRequestType;

    public Geofencing(Activity activityContext) {
        // Initialize variables
        mCallingActivity = activityContext;
        mLog = new Logger(mCallingActivity);
        mGeofencePendingIntent = null;
        // Retrieve an instance of the SharedPreferences object.
        mSharedPreferences = mCallingActivity.getSharedPreferences("GeofencingPreferences", mCallingActivity.MODE_PRIVATE);
        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();
    }

    // Builds a GoogleApiClient to request access to Location Services
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mCallingActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    // Request connection to Location Services
    public void requestConnection() {
        mGoogleApiClient.connect();
    }
    // Request disconnection from Location Services
    public void requestDisconnection() {
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        Message status = new Message(APPTAG, "Connected to GoogleApiClient");
        mLog.addMessage(status);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Message status = new Message(APPTAG, "Connected to GoogleApiClient failed. Error code " + result.getErrorCode());
        mLog.addMessage(status);
    }

    // Connection lost for some reason; onConnected() will be called again automatically when the service reconnects
    @Override
    public void onConnectionSuspended(int cause) {
        Message status = new Message(APPTAG, "Connected to GoogleApiClient suspended");
        mLog.addMessage(status);
    }

    /**
     * Builds and returns a GeofencingRequest. Specifies the list of geofences to be monitored.
     * Also specifies how the geofence notifications are initially triggered.
     */
    private GeofencingRequest getGeofencingRequest(ArrayList<Geofence> geofenceList) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        // Notify when geofence created and devices is already in it
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(geofenceList);
        // Return a GeofencingRequest.
        return builder.build();
    }

    // Adds geofences for monitoring
    public void addGeofences(ArrayList<Geofence> geofenceList) {
        if (!mGoogleApiClient.isConnected()) {
            return;
        }
        try {
            // Set request type
            mRequestType = AppParams.REQUEST_TYPE.ADD;
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    getGeofencingRequest(geofenceList),
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            logSecurityException(securityException);
        }
    }

    // Stops monitoring of geofences
    public void removeGeofences(ArrayList<String> geofenceIds) {
        if (!mGoogleApiClient.isConnected()) {
            return;
        }
        try {
            // Set request type
            mRequestType = AppParams.REQUEST_TYPE.REMOVE;
            // Remove geofences.
            LocationServices.GeofencingApi.removeGeofences(
                    mGoogleApiClient,
                    geofenceIds
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            logSecurityException(securityException);
        }
    }

    private void logSecurityException(SecurityException securityException) {
        Message status = new Message(APPTAG, "Invalid location permission.  " +
                                            "You need to use ACCESS_FINE_LOCATION with geofences");
        status.setExtra(securityException.toString());
        mLog.addMessage(status);
    }

    // Handle result of add and remove geofences
    public void onResult(Status status) {
        if (status.isSuccess()) {
            Message success;
            if (mRequestType == AppParams.REQUEST_TYPE.ADD) {
                success = new Message(APPTAG, "Adding geofences was successful");
                mLog.addMessage(success);
            }else{
                success = new Message(APPTAG, "Removing geofences was successful");
                mLog.addMessage(success);
            }
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = AppParams.getErrorString(mCallingActivity, status.getStatusCode());
            Message error = new Message(APPTAG, errorMessage);
            mLog.addMessage(error);
        }
    }

    // Gets a PendingIntent to send with the request to add or remove Geofences
    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(mCallingActivity, GeofenceTransitionsIntentService.class);
        // Use FLAG_UPDATE_CURRENT so that the same pending intent is returned when calling addGeofences() and removeGeofences()
        return PendingIntent.getService(mCallingActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    

}
