package com.lubo.comp3200.context_recognition_user_test;


import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * Created by Lubo on 12.2.2015.
 *
 * Service that handles updates received from the ActivityRecognitionClient
 */
public class ActivityRecognitionService extends IntentService {

    // Tag for logs
    private final String APPTAG = "ActivityRecognitionService";
    // Custom logger
    private Logger mLog;
    // Current activity
    private AppParams.Activity mCurrentActivity;
    private List<DetectedActivity> mProbableActivities;
    private ContextStore mContextStore;

    public ActivityRecognitionService() {
        super("ActivityRecognitionService");
    }

    // Handles activity recognition updates
    @Override
    protected void onHandleIntent(Intent intent) {
        // Instantiate the logger
        mLog = new Logger(this);
        Message status = new Message(APPTAG, "Activity recognition service started");
        // Extract the result of the update if it exists
        if(ActivityRecognitionResult.hasResult(intent)) {
            // Get the activity of the active context if there is one
            mCurrentActivity = AppParams.Activity.valueOf(intent.getStringExtra(AppParams.CURRENT_CONTEXT_ACTIVITY));
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            DetectedActivity activity = result.getMostProbableActivity();
            mProbableActivities = result.getProbableActivities();
            int activityType = activity.getType();
            int confidence = activity.getConfidence();
            AppParams.Activity detectedContextActivity = determineActivity(activityType);
            if (confidence > AppParams.MINIMUM_CONFIDENCE){
                if(mCurrentActivity != null){
                    if (detectedContextActivity != mCurrentActivity){
                        //TODO: initiate context recognition service
                    }
                }else{
                    Main.mScheduler.getInitialContext().setActivity(detectedContextActivity);
                    ContextParams.CURRENT_ACTIVITY = detectedContextActivity;
                }
            }

        }
    }

    // Determines the context activity parameter
    private AppParams.Activity determineActivity(int activity){
        switch(activity){
            case DetectedActivity.STILL:
                return AppParams.Activity.NONE;
            case DetectedActivity.WALKING:
                return AppParams.Activity.WALKING;
            case DetectedActivity.RUNNING:
                return AppParams.Activity.RUNNING;
            case DetectedActivity.ON_FOOT:
                return resolveOnFoot(mProbableActivities);
            case DetectedActivity.ON_BICYCLE:
                return AppParams.Activity.CYCLING;
            case DetectedActivity.IN_VEHICLE:
                return resolveInVehicle();
            default:
                return AppParams.Activity.NONE;
        }
    }

    // Determines whether the activity is walking or running if the service returns ON_FOOT
    private AppParams.Activity resolveOnFoot(List<DetectedActivity> probableActivities){
        int walkingConfidence = 0;
        int runningConfidence = 0;
        for (DetectedActivity da : probableActivities){
            if (da.getType() == DetectedActivity.WALKING) {
                walkingConfidence = da.getConfidence();
            }
            if (da.getType() == DetectedActivity.RUNNING) {
                runningConfidence = da.getConfidence();
            }
        }
        if (runningConfidence > walkingConfidence) {
            return AppParams.Activity.RUNNING;
        }
        return AppParams.Activity.WALKING;
    }

    // Determine whether activity is commuting or travelling
    private AppParams.Activity resolveInVehicle() {
        return AppParams.Activity.NONE;
    }

}
