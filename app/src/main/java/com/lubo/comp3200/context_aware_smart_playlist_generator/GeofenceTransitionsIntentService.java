package com.lubo.comp3200.context_aware_smart_playlist_generator;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class receives geofence transition events from Location Services, in the
 * form of an Intent containing the transition type and geofence id(s) that triggered
 * the event.
 *
 * Code heavily based on Google's examples
 */
public class GeofenceTransitionsIntentService extends IntentService {

    // Tag for logs
    private static final String APPTAG = "GeofenceTransitionsIntentService";
    // Instance of the logger class
    private Logger mLog;


    // Sets an identifier for this class' background thread
    public GeofenceTransitionsIntentService() {
        // Worker thread name
        super(APPTAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // Handles incoming intents from Location services
    @Override
    protected void onHandleIntent(Intent intent) {
        // Instantiate the logger
        mLog = new Logger(this);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = AppParams.getErrorString(this, geofencingEvent.getErrorCode());
            Message error = new Message(APPTAG, errorMessage);
            mLog.addMessage(error);
            return;
        }
        // Get the transition type
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(this, geofenceTransition, triggeringGeofences);
        }
    }

    // Gets transition details and returns them as a formatted string
    private String getGeofenceTransitionDetails(Context context, int geofenceTransition, List<Geofence> triggeringGeofences) {
        String geofenceTransitionString = getTransitionString(geofenceTransition);
        // Get the Ids of each geofence that was triggered.
        ArrayList triggeringGeofencesIdsList = new ArrayList();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ",  triggeringGeofencesIdsList);
        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }

    // Maps geofence transition types to their human-readable equivalents
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.geofence_transition_unknown);
        }
    }
}
