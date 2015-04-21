package com.lubo.comp3200.context_aware_smart_playlist_generator;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

/**
 * Created by Lubo on 11.2.2015.
 *
 * A class that starts or stops the scanning of user activity
 */
public class ActivityRecognitionScan implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    // Reference to the calling activity
    private final Activity mActivity;
    // PendingIntent used to send updates about the activity
    private PendingIntent mActivityScanPendingIntent;
    // GoogleApiClient to connect to activity recognition services
    private GoogleApiClient mGoogleApiClient;
    // Custom logger
    private Logger log;
    // Tag for logs
    private final String APPTAG = "ActivityRecgonitionScan";
    // The activity of the active context
    private AppParams.Activity mCurrentActivity;

    public ActivityRecognitionScan(Activity activityContext, AppParams.Activity activity){
        // Save the context
        mActivity = activityContext;
        mCurrentActivity = activity;
        // Instantiate logger
        log = new Logger(activityContext);
    }

    // Start the scanning of activity recognition - activity won't be recognised until the client has connected
    public void startActivityRecognitionScan() {
        // Instantiate the ActivityRecognitionClient and connect it to Location Services
         mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        Message status = new Message(APPTAG, "Requested connection to Activity Recognition services");
        log.addMessage(status);
    }

    // Stop activity recognition scanning
    public void stopActivityRecognitionScan(){
        try{
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mGoogleApiClient, mActivityScanPendingIntent);
            Message status = new Message(APPTAG, "Activity recognition scan stopped");
            log.addMessage(status);
        }catch (Exception e){
            // Most likely the scan was not set up
            String msg = "Error stoping activity recognition scan. Make sure the scan was started";
            Message status = new Message(APPTAG, msg);
            log.addMessage(status);
        }

    }

    // Once the connection has been established, make a request for activity updates
    @Override
    public void onConnected(Bundle bundle) {
        Message status = new Message(APPTAG, "Activity Recognition client connected");
        log.addMessage(status);
        Intent intent = new Intent(mActivity, ActivityRecognitionService.class);
        intent.putExtra(AppParams.CURRENT_CONTEXT_ACTIVITY, mCurrentActivity.toString());
        mActivityScanPendingIntent = PendingIntent.getService(mActivity,
                                                                0,
                                                                intent,
                                                                PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mGoogleApiClient, AppParams.ACTIVITY_UPDATE_INTERVAL, mActivityScanPendingIntent);
        status = new Message(APPTAG, "Activity recognition updates requested");
        log.addMessage(status);
    }

    // When disconnected, log a message and destroy the client
    @Override
    public void onConnectionSuspended(int i) {
        Message status = new Message(APPTAG, "Client disconnected");
        log.addMessage(status);
        mGoogleApiClient = null;
    }

    // If a connection request fails, log the error and try to resovle it
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // If the error has a resolution, GooglePlay services can attempt to resolve it
        Message status;
        if(connectionResult.hasResolution()){
            try{
                connectionResult.startResolutionForResult(mActivity, AppParams.CONNECTION_FAILURE_RESOLUTION_REQUEST);
                status = new Message(APPTAG, "Connection error. Attempting resolution");
                log.addMessage(status);
            } catch (IntentSender.SendIntentException e) {
                String error = Log.getStackTraceString(e);
                status = new Message(APPTAG, "Connection error could not be resolved");
                status.setExtra(error);
                log.addMessage(status);
            }
        }
    }
}
